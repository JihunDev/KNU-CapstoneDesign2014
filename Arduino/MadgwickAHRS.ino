#define beta 0.0756
#define zeta 0.00303


float q1 = 0;
float q2 = 0;
float q3 = 0;
float q4 = 1;

float bx = 1;
float bz = 0;

float wbx = 0;
float wby = 0;
float wbz = 0;


//-----------------------------------------------------------------------------
//
void MadgwickAHRS(float wx, float wy, float wz, float ax, float ay, float az, float mx, float my, float mz, float dt, float *phi, float *theta, float*psi)
{
  float norm;
  
  float f1;
  float f2;
  float f3;
  float f4;
  float f5;
  float f6;

  float ex;
  float ey;
  float ez;

  float dq1_w;
  float dq2_w;
  float dq3_w;
  float dq4_w;

  float dq1_e;
  float dq2_e;
  float dq3_e;
  float dq4_e;

  
  norm = sqrt(ax*ax + ay*ay + az*az);
  ax /= norm;
  ay /= norm;
  az /= norm;

  norm = sqrt(mx*mx + my*my + mz*mz);
  mx /= norm;
  my /= norm;
  mz /= norm;

  f1 = 2*(q1*q3 - q2*q4)     + ax;               // g - a  in body axes
  f2 = 2*(q2*q3 + q4*q1)     + ay;
  f3 = 1 - 2*(q1*q1 + q2*q2) + az;
  f4 = bx*(1-2*(q2*q2 + q3*q3)) + bz*2*(q1*q3 - q2*q4)   - mx;
  f5 = bx*2*(q2*q1 - q3*q4)     + bz*2*(q2*q3 + q1*q4)   - my;
  f6 = bx*2*(q3*q1 + q2*q4)     + bz*(1-2*(q1*q1+q2*q2)) - mz;

  dq1_e =  2*q3*f1 + 2*q4*f2 - 4*q1*f3 + 2*bz*q3*f4           + (2*bx*q2+2*bz*q4)*f5 + (2*bx*q3-4*bz*q1)*f6;          // J'*f
  dq2_e = -2*q4*f1 + 2*q3*f2 - 4*q2*f3 - (4*bx*q2+2*bz*q4)*f4 + (2*bx*q1+2*bz*q3)*f5 + (2*bx*q4-4*bz*q2)*f6;
  dq3_e = -2*q1*f1 + 2*q2*f2           - (4*bx*q3-2*bz*q1)*f4 - (2*bx*q4-2*bz*q2)*f5 + 2*bx*q1*f6;
  dq4_e = -2*q2*f1 + 2*q1*f2           - 2*bz*q2*f4           - (2*bx*q3-2*bz*q1)*f5 + 2*bx*q2*f6;

  norm = sqrt(dq1_e*dq1_e + dq2_e*dq2_e + dq3_e*dq3_e + dq4_e*dq4_e);
  dq1_e /= norm;
  dq2_e /= norm;
  dq3_e /= norm;
  dq4_e /= norm;

  ex = 2*( q4*dq1_e + q3*dq2_e - q2*dq3_e - q1*dq4_e);
  ey = 2*(-q3*dq1_e + q4*dq2_e + q1*dq3_e - q2*dq4_e);
  ez = 2*( q2*dq1_e - q1*dq2_e + q4*dq3_e - q3*dq4_e);

  wbx += dt*zeta*ex;
  wby += dt*zeta*ey;
  wbz += dt*zeta*ez;

  wx -= wbx;
  wy -= wby;
  wz -= wbz;

  dq1_w = 0.5*( q4*wx - q3*wy + q2*wz);    // Kinematic eq.
  dq2_w = 0.5*( q3*wx + q4*wy - q1*wz);
  dq3_w = 0.5*(-q2*wx + q1*wy + q4*wz);
  dq4_w = 0.5*(-q1*wx - q2*wy - q3*wz);

  q1 += (dq1_w - beta*dq1_e)*dt;
  q2 += (dq2_w - beta*dq2_e)*dt;
  q3 += (dq3_w - beta*dq3_e)*dt;
  q4 += (dq4_w - beta*dq4_e)*dt;

  norm = sqrt(q1*q1 + q2*q2 + q3*q3 + q4*q4);
  q1 /= norm;
  q2 /= norm;
  q3 /= norm;
  q4 /= norm;

  *phi   =  atan2(2*q2*q3 + 2*q1*q4, 1 - 2*(q1*q1 + q2*q2));
  *theta = -asin(2*q1*q3 - 2*q2*q4);
  *psi   =  atan2(2*q1*q2 + 2*q3*q4, 1 - 2*(q2*q2 + q3*q3));

  float hx = mx*(1-2*(q2*q2+q3*q3)) + my*2*(q2*q1-q3*q4)     + mz*2*(q3*q1+q2*q4);
  float hy = mx*2*(q1*q2+q3*q4)     + my*(1-2*(q1*q1+q3*q3)) + mz*2*(q3*q2-q1*q4);
  float hz = mx*2*(q1*q3-q2*q4)     + my*2*(q2*q3+q1*q4)     + mz*(1-2*(q1*q1+q2*q2));

  bx = sqrt(hx*hx + hy*hy);
  bz = hz;
}

