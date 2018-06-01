
void QuatGyro(float p, float q, float r, float dt, float *gphi, float *gtheta, float *gpsi)
{
  static float pQ1 = 1;
  static float pQ2 = 0;
  static float pQ3 = 0;
  static float pQ4 = 0;

  float q1;
  float q2;
  float q3;
  float q4;

  q1 = pQ1 + 0.5*dt*(  0   - p*pQ2 - q*pQ3 - r*pQ4);
  q2 = pQ2 + 0.5*dt*(p*pQ1 +   0   + r*pQ3 - q*pQ4);
  q3 = pQ3 + 0.5*dt*(q*pQ1 - r*pQ2 +   0   + p*pQ4);
  q4 = pQ4 + 0.5*dt*(r*pQ1 + q*pQ2 - p*pQ3 +   0  );

  *gphi   =  atan2(2*q3*q4 + 2*q1*q2, 1 - 2*q2*q2 - 2*q3*q3);
  *gtheta = -asin(2*q2*q4 - 2*q1*q3);
  *gpsi   =  atan2(2*q2*q3 + 2*q1*q4, 1 - 2*q3*q3 - 2*q4*q4); 
  
  pQ1 = q1;
  pQ2 = q2;
  pQ3 = q3;
  pQ4 = q4;
}
