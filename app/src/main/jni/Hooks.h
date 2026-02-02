
// public class Component -> public Transform get_transform() { }
void *getTransform(void *player) {
    if (!player) return NULL;
    static const auto get_transform_injected = reinterpret_cast<uint64_t(__fastcall *)(void *)>(getAbsoluteAddress("libil2cpp.so", 0x30DA4B8));
    return (void *) get_transform_injected(player);
}

// public class Transform -> private static void get_position_Injected(IntPtr _unity_self, out Vector3 ret) { }
Vector3 get_position(void *transform) {
    if (!transform)return Vector3();
    Vector3 position;
    static const auto get_position_injected = reinterpret_cast<uint64_t(__fastcall *)(void *,Vector3 &)>(getAbsoluteAddress("libil2cpp.so", 0x30F08E0));
    get_position_injected(transform, position);
    return position;
}

// public sealed class Camera -> public Vector3 WorldToScreenPoint(Vector3 position, Camera.MonoOrStereoscopicEye eye) { }

Vector3 WorldToScreenPoint(void *transform, Vector3 test) {
    if (!transform)return Vector3();
    Vector3 position;
    static const auto WorldToScreenPoint_Injected = reinterpret_cast<uint64_t(__fastcall *)(void *,Vector3, int, Vector3 &)>(getAbsoluteAddress("libil2cpp.so", 0x30747CC));
    WorldToScreenPoint_Injected(transform, test, 4, position);
    return position;
}

//public sealed class Camera -> public static Camera get_main() { }
void *get_camera() {
    static const auto get_camera_injected = reinterpret_cast<uint64_t(__fastcall *)()>(getAbsoluteAddress("libil2cpp.so", 0x3074DE0));
    return (void *) get_camera_injected();
}

Vector3 GetPlayerLocation(void *player) {
    return get_position(getTransform(player));
}

float GetPlayerHealth(void *player) {
    return *(bool *) ((uint64_t) player + 0x40);
}

float GetMaxHealth(void *player) {
    return (int) 100;
}

bool PlayerAlive(void *player) {
    return player != NULL && GetPlayerHealth(player) > 0;
}

bool IsPlayerDead(void *player) {
    return player == NULL && GetPlayerHealth(player) < 1;
}

string float_to_string (int value) 
{
    string str; 
    str = std::to_string(value);
    str += "M";
    return str;
}
