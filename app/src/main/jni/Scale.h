#include "Vector3.h"

Vector3 GetPlayerLocation(void *player) {
    Vector3 location;
    location = get_position(get_transform(player));
    return location;
}
Vector3 get_position(void *transform) {
    if (!transform)
    return Vector3();
    Vector3 position;
    static const auto get_position_injected = reinterpret_cast<uint64_t(__fastcall *)(void *,
    Vector3 &)>(getAbsoluteAddress(targetLibName, 0x22ABEC8));
    get_position_injected(transform, position);
    return position;
}

bool enter;
float Run = 1;
bool GetForward1;
bool telekill;
bool Fly = false;
float YAxis = 1;
bool Fly1 = false;
float YAxis1 = 1;
bool enemyscale;
float Forward;
int scaleX, scaleY, scaleZ;

void* (*Component_GetTransform)(void* component);
void (*set_eulerAngles)(void* transform, Vector3 newPosition);
void (*GetForward)(void* transform, Vector3 Forward);
void (*set_localScale_Injected)(void *transform, Vector3 scale);
void (*set_position)(void* transform, Vector3 newPosition);
void (*old_policesize)(void *Player);
void policesize(void *Player) {
    if (Player != NULL) {
        
              if (GetForward1 && Forward) {
                Vector3 enemyLocation = GetPlayerLocation(Player);
                GetForward(Component_GetTransform(Player), Vector3(Forward, Forward, Forward));;
                } else {
                GetForward(Component_GetTransform(Player), Vector3(1, 1, 1));
                }      
             Vector3 PlayerLocation = GetPlayerLocation(Player);
         if (Fly1 && YAxis1 > 1) {
          set_eulerAngles(Component_GetTransform(Player),
           Vector3(PlayerLocation.x, PlayerLocation.y = (float) YAxis1,- PlayerLocation.z));
    }          
            
            if (telekill) {
                Vector3 enemyLocation = GetPlayerLocation(Player);
                set_eulerAngles(Component_GetTransform(Player), Vector3(enemyLocation.x, enemyLocation.y, enemyLocation.z - 1));
            }
            Vector3 PlayerLocation1 = GetPlayerLocation(Player);
         if (Fly && YAxis > 1) {
          GetForward(Component_GetTransform(Player),
           Vector3(PlayerLocation1.x, PlayerLocation1.y = (float) YAxis, PlayerLocation1.z));
    }
                if (enemyscale) {      
 
                set_localScale_Injected(get_transform(Player), Vector3(scaleX, scaleY, scaleZ));
                } else {
                set_localScale_Injected(get_transform(Player), Vector3(1, 1, 1));
                }
            Vector3 Run1 = GetPlayerLocation(Player);
         if (enter && Run > 1) {
          set_position(Component_GetTransform(Player),
           Vector3(Run1.x, Run1.y = (float) Run,- Run1.z));
    }          
             }
    
    old_policesize(Player);
}   
