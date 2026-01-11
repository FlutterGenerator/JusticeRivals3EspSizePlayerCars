float NormalizeAngle (float angle){
    while (angle>360)
        angle -= 360;
    while (angle<0)
        angle += 360;
    return angle;
}

Vector3 NormalizeAngles (Vector3 angles){
    angles.x = NormalizeAngle (angles.x);
    angles.y = NormalizeAngle (angles.y);
    angles.z = NormalizeAngle (angles.z);
    return angles;
}

Vector3 ToEulerRad(Quaternion q1){
    float Rad2Deg = 360.0f / (M_PI * 2.0f);

    float sqw = q1.w * q1.w;
    float sqx = q1.x * q1.x;
    float sqy = q1.y * q1.y;
    float sqz = q1.z * q1.z;
    float unit = sqx + sqy + sqz + sqw;
    float test = q1.x * q1.w - q1.y * q1.z;
    Vector3 v;

    if (test>0.4995f*unit) {
        v.y = 2.0f * atan2f (q1.y, q1.x);
        v.x = M_PI / 2.0f;
        v.z = 0;
        return NormalizeAngles(v * Rad2Deg);
    }
    if (test<-0.4995f*unit) {
        v.y = -2.0f * atan2f (q1.y, q1.x);
        v.x = -M_PI / 2.0f;
        v.z = 0;
        return NormalizeAngles (v * Rad2Deg);
    }
    Quaternion q(q1.w, q1.z, q1.x, q1.y);
    v.y = atan2f (2.0f * q.x * q.w + 2.0f * q.y * q.z, 1 - 2.0f * (q.z * q.z + q.w * q.w)); // yaw
    v.x = asinf (2.0f * (q.x * q.z - q.w * q.y)); // pitch
    v.z = atan2f (2.0f * q.x * q.y + 2.0f * q.z * q.w, 1 - 2.0f * (q.y * q.y + q.z * q.z)); // roll
    return NormalizeAngles (v * Rad2Deg);
}

Quaternion GetRotationToLocation(Vector3 targetLocation, float y_bias, Vector3 myLoc){
    return Quaternion::LookRotation((targetLocation + Vector3(0, y_bias, 0)) - myLoc, Vector3(0, 1, 0));
}

