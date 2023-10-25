# Inertial Measurement Unit (IMU) 
IMU stands for Inertial Measurement Unit. It is an electronic device that measures and reports a body's specific force, angular rate, and sometimes the magnetic field surrounding the body, using a combination of accelerometers, gyroscopes, and sometimes magnetometers. IMUs are commonly used in applications such as drones, robotics, and virtual reality systems.

## Aerospace Applications
In the case of MRT we use a 9-DOF (Degrees of Freedom) IMU to capture all necessary data. a 9-DOF IMU includes an accelerometer, magnetometer, and a gyroscope, each in 3-DOF. These valus allow for finding and calculating the attitude and space orientation of the system, meaning you can find the roll, yaw, and pitch of the system.

# MRT Use Case
For MRT, the team elected to use an ICM-20948, a 9-DOF IMU. 

## ICM-20948 Setup and Use
Implementation of the IMU was done through I2C, as we are utilizing SparkFun's Qwicc Connectors.

### IMU Initialization
To initialize the I2C connection, we used the follow lines of code.

```Arduino
ICM_20948_I2C myICM; // Uses default I2C address 0x69
#define WIRE_PORT Wire
#define AD0_VAL 1
```

This creates the IMU as a Arduino object, and we can then move forward to actually setting it up for use. This is done through the following line:

```Arduino
 myICM.begin(WIRE_PORT, AD0_VAL); /// Initialization of the ICM
```

We can then ensure that setup went proper through the following set of lines:

```Arduino
  Serial.print(F("Initialization of the sensor returned: "));
  Serial.println(myICM.statusString());
  // It should return 'All is well' if the program initialized correctly
  ```
This leaves us with the complete loop and initialization seen below.
```Arduino
#include "src/ICM_20948.h"

BME280 ES_Sens;      // Uses default I2C address 0x77
ICM_20948_I2C myICM; // Uses default I2C address 0x69
#define WIRE_PORT Wire
#define AD0_VAL 1

void setup(){
Serial.begin(115200);
  while (!Serial)
  {
  };

  Wire.begin();
  myICM.begin(WIRE_PORT, AD0_VAL);

  WIRE_PORT.begin();
  WIRE_PORT.setClock(400000);

  Serial.print(F("Initialization of the sensor returned: "));
  Serial.println(myICM.statusString());
  if (myICM.status != ICM_20948_Stat_Ok)
  {
    Serial.println("Trying again...");
    delay(500);
  }
  else
  {
    Serial.println("Success!");
    initialized = true;
  }
}
```
There is also some extra logic that exists for this, checking the status using 
```Arduino
myICM.status
```
which is a simnple value assigned by the IMU for it's status.

### Data Collection (Acc, Gyro, & Mag)
With the IMU now recognized as an object, and now that it is known that it is operating, we can actually work on developing a function which pulls the data from the IMU. For our use case, I really only care about the Accelerometer, Gyroscope, and Magnetometer. Due to that, I will setup my loop and function.

