//-----------------------------------------------------------------------------
// 9Axis Senser + Bluetooth
//
// H/W
// MintIMU + HC-06
// MintIMU(MPU6000(1), MPU6000(2), HMC5883L, Arduino Leonardo)
//
// Coder : Kermit
// Mail : holidays8903@gmail.com
// 2014.06.00
//
// DTR,RTS -> Enable
//-----------------------------------------------------------------------------
#include <SPI.h>          
#include <I2C.h>                                                                       
#include <NavIMU.h>                    
#include <NavCompass.h>
//#include <SoftwareSerial.h> 

//9-Axis
const float R2D = 57.296;
const int SPI_CS_MPU6000 = 7;

NavIMU     imu(SPI_CS_MPU6000);
NavCompass compass;

//-----------------------------------------------------------------------------

void setup()
{
  delay(2000);

  Serial.begin(9600);

  while(! Serial)
  {
    ;  
  }
  
  Serial.println("CLEARDATA");
  Serial.println("LABEL,TIME,Roll,Pitch,yaw,Acc-x,Acc-y,Acc-z,Gyro-x,Gyro-y,Gyro-z,Com-x,Com-y,Com-z,");
  
  SPI.begin();
  SPI.setClockDivider(SPI_CLOCK_DIV16); // 1 MHz rate

  pinMode(SPI_CS_MPU6000, OUTPUT);
  digitalWrite(SPI_CS_MPU6000, HIGH);  
  delay(1);
   
  imu.init();

  compass.init();
  compass.set_offsets(-81, -33, -14);
  
}

//-----------------------------------------------------------------------------

void loop()
{
  static unsigned long loopEpoch;
  static unsigned int  loopCount;

  if (millis() - loopEpoch < 9) 
  {
    return;
  }
  
  loopEpoch = millis();

  float dt;
  float wx, wy, wz;
  float ax, ay, az;
  float mx, my, mz;
  float phi, theta, psi;
  float gphi, gtheta, gpsi;
  
  //IMU Senser-Update
  imu.update();

  imu.get_accel(&ax, &ay, &az);
  imu.get_gyro(&wx, &wy, &wz);
  // wx-p wy-q wz-r
  
  dt = imu.get_sample_time();

  //Compass Senser-Update
  compass.update();
  delay(10);
  compass.get_magnet(&mx, &my, &mz);
  
  //AHRS fiter
  MadgwickAHRS(wx, wy, wz, ax, ay, az, mx, my, mz, dt, &phi, &theta, &psi);

  //Packet    
  unsigned int value;
  
  int packet[11];
   
  packet[0] = phi*R2D;
  packet[1] = theta*R2D;
  packet[2] = psi*R2D;
  //IMU Senser-Update
  imu.update();

  imu.get_accel(&ax, &ay, &az);
  imu.get_gyro(&wx, &wy, &wz);
  
  //Euler angle
  QuatGyro(wx, wy, wz, dt, &gphi, &gtheta, &gpsi);
  
  //Compass Senser-Update
  compass.update();
  delay(10);
  compass.get_magnet(&mx, &my, &mz);

  // Accel - ax, ay, az
  packet[3] = ax;
  packet[4] = ay;
  packet[5] = az;
  
  // Gyro - wx, wy, wz
  packet[6] = gphi;
  packet[7] = gtheta;
  packet[8] = gpsi;
  
  // Compass - mx, my, mz
  packet[9] = mx;
  packet[10] = my;
  packet[11] = mz;
  
  Serial.print("DATA,TIME,");  
  Serial.print(packet[0],DEC);
    Serial.print(",");
  Serial.print(packet[1],DEC);
    Serial.print(",");  
  Serial.print(packet[2],DEC);
    Serial.print(",");
  Serial.print(packet[3],DEC);
    Serial.print(",");
  Serial.print(packet[4],DEC);
    Serial.print(",");
  Serial.print(packet[5],DEC);
    Serial.print(",");
  Serial.print(packet[6],DEC);
    Serial.print(",");
  Serial.print(packet[7],DEC);
    Serial.print(",");
  Serial.print(packet[8],DEC);
    Serial.print(",");
  Serial.print(packet[9],DEC);
    Serial.print(",");
  Serial.print(packet[10],DEC);
    Serial.print(",");
  Serial.print(packet[11],DEC);
    Serial.println("");   
                  
  /*  

  //Pacekt-print
  for(int c = 0; c <=11; c++)
  {
      Serial1.print(packet[c], DEC);
      if(c<=10)
      {
      Serial1.print(" ");
      }
      if(c == 11)
      { 
        //Serial.println(" ");
        delay(1000);
        break;
      }
  }
*/  
}

