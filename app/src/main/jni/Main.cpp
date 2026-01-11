#include <list>
#include <vector>
#include <string.h>
#include <pthread.h>
#include <cstring>
#include <jni.h>
#include <unistd.h>
#include <fstream>
#include <iostream>
#include "Includes/Logger.h"
#include "Includes/obfuscate.h"
#include "Includes/Utils.h"
#include "KittyMemory/MemoryPatch.h"
#include "Menu.h"
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>
#include "Unity/Vector2.h"
#include "Unity/Vector3.h"
#include "Unity/Rect.h"
#include "Unity/Color.h"
#include "Unity/Quaternion.h"
#include "Includes/MonoString.h"
#include "Includes/Strings.h"
#include "PoisonModzDraw.h"
#include "Hooks.h"

#include "ESPManager.h"
#include "Includes/Chams.h"
#include "Includes/Macros.h"
#define targetLibName OBFUSCATE("libil2cpp.so")
#if defined(__aarch64__) //Compile for arm64 lib only
#include <And64InlineHook/And64InlineHook.hpp>
#else //Compile for armv7 lib only. Do not worry about greyed out highlighting code, it still works

#include <Substrate/SubstrateHook.h>
#include <Substrate/CydiaSubstrate.h>

#endif

ESPManager *espManager;
PoisonModzEsp es;


struct My_Patches {
    MemoryPatch 
	
byme;} hexPatches;
bool cheat;
bool ESP,ESPCAR,ESPLine, ESPBox, ESPBox3d, ESPCircle, ESPName, ESPHealth, ESPDistance, ESPObject, ESP3d, PlayerCircle, ESPCrossHair, ESPTeammate, ESPCorner;
bool chams, shading, wireframe, glow, outline, rainbow = false;
int CrossSize = 5;
float playerTextSize = 5.0f;

bool Tracer;
float thickness = 1.5f;
float isLocalPlayerSize;
float isLocalCarSize;
float isLocalMapSize;
void (*SetLocalScale)(void *transform, Vector3);
void (*AddMoneyExample)(void *instance, int amount);
Color color = Color(0, 255, 255, 255);
struct {
int PositionLine = 1;
int PositionLook = 1;
} Setting;

bool(*BypassESP)(void* _this);
bool _BypassESP(void* _this) {
    return true;
}

void *playerlate = NULL;
void *get_Player(void *player) {
    playerlate = player;
    return playerlate;
}

Vector3 add(Vector3 a, Vector3 b) {
    Vector3 c;
    c.x= a.x + b.x;
    c.y = a.y + b.y;
    c.z = a.z + b.z;
    return c;
}

// Khadhar

void Draw3dBox(PoisonModzEsp esp,Color espColor, Vector3 Transform,void * camera,int glHeight,int glWidth)
{
    Vector3 position2 = add(Transform, Vector3(0.6, 1.6, 0.6)); 
    Vector3 position3 = add(Transform, Vector3(0.6, 0, 0.6));
    Vector3 position4 = add(Transform, Vector3(-0.5, 0, 0.6)); 
    Vector3 position5 = add(Transform, Vector3(-0.5, 1.6, 0.6));
    Vector3 position6 = add(add(Transform, Vector3(0.6, 1.6, 0)), Vector3(0, 0, -0.6));
    Vector3 position7 = add(add(Transform, Vector3(0.6, 0, 0)), Vector3(0, 0, -0.6));
    Vector3 position8 = add(add(Transform, Vector3(-0.5, 0, 0)), Vector3(0, 0, -0.6)); 
    Vector3 position9 = add(add(Transform, Vector3(-0.5, 1.6, 0)), Vector3(0, 0, -0.6));
    Vector3 position10 = add(Transform, Vector3(0.6, 1.6, 0.6)); 
    Vector3 position11 = add(Transform, Vector3(0.6, 0, 0.6));
    Vector3 position12 = add(Transform, Vector3(-0.5, 0, 0.6)); 
    Vector3 position13 = add(Transform, Vector3(-0.5, 1.6, 0.6));
    Vector3 position14 = add(add(Transform, Vector3(0.6, 1.6, 0)), Vector3(0, 0, -0.6));
    Vector3 position15 = add(add(Transform, Vector3(0.6, 0, 0)), Vector3(0, 0, -0.6));
    Vector3 position16 = add(add(Transform, Vector3(-0.5, 0, 0)), Vector3(0, 0, -0.6)); 
    Vector3 position17 = add(add(Transform, Vector3(-0.5, 1.6, 0)), Vector3(0, 0, -0.6));

	
	
    Vector3 vector = WorldToScreenPoint(camera, position2);
    Vector3 vector2 = WorldToScreenPoint(camera, position3);
    Vector3 vector3 = WorldToScreenPoint(camera, position4);
    Vector3 vector4 = WorldToScreenPoint(camera, position5);
    Vector3 vector5 = WorldToScreenPoint(camera, position6);
    Vector3 vector6 = WorldToScreenPoint(camera, position7);
    Vector3 vector7 = WorldToScreenPoint(camera, position8);
    Vector3 vector8 = WorldToScreenPoint(camera, position9);
    Vector3 vector9 = WorldToScreenPoint(camera, position10);
    Vector3 vector10 = WorldToScreenPoint(camera, position11);
    Vector3 vector11 = WorldToScreenPoint(camera, position12);
    Vector3 vector12 = WorldToScreenPoint(camera, position13);
    Vector3 vector13 = WorldToScreenPoint(camera, position14);
    Vector3 vector14 = WorldToScreenPoint(camera, position15);
    Vector3 vector15 = WorldToScreenPoint(camera, position16);
    Vector3 vector16 = WorldToScreenPoint(camera, position17);

	
	
    if (vector.z > 0 && vector2.z > 0 && vector3.z > 0 && vector4.z > 0 && vector5.z > 0 && vector6.z > 0 && vector7.z > 0  && vector8.z > 0  && vector9.z > 0  && vector10.z > 0  && vector11.z > 0  && vector12.z > 0  && vector13.z > 0  && vector14.z > 0  && vector15.z > 0 && vector16.z > 0 )
    {
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector.x)), (glHeight - vector.y)), Vector2((glWidth - (glWidth - vector2.x)), (glHeight - vector2.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector3.x)), (glHeight - vector3.y)), Vector2((glWidth - (glWidth - vector2.x)), (glHeight - vector2.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector.x)), (glHeight - vector.y)), Vector2((glWidth - (glWidth - vector4.x)), (glHeight - vector4.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector4.x)), (glHeight - vector4.y)), Vector2((glWidth - (glWidth - vector3.x)), (glHeight - vector3.y)));
        
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector5.x)), (glHeight - vector5.y)), Vector2((glWidth - (glWidth - vector6.x)), (glHeight - vector6.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector7.x)), (glHeight - vector7.y)), Vector2((glWidth - (glWidth - vector6.x)), (glHeight - vector6.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector5.x)), (glHeight - vector5.y)), Vector2((glWidth - (glWidth - vector8.x)), (glHeight - vector8.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector8.x)), (glHeight - vector8.y)), Vector2((glWidth - (glWidth - vector7.x)), (glHeight - vector7.y)));
 
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector.x)), (glHeight - vector.y)), Vector2((glWidth - (glWidth - vector5.x)), (glHeight - vector5.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector2.x)), (glHeight - vector2.y)), Vector2((glWidth - (glWidth - vector6.x)), (glHeight - vector6.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector3.x)), (glHeight - vector3.y)), Vector2((glWidth - (glWidth - vector7.x)), (glHeight - vector7.y)));
        esp.DrawLine(espColor, thickness, Vector2((glWidth -(glWidth -vector4.x)), (glHeight - vector4.y)), Vector2((glWidth - (glWidth - vector8.x)), (glHeight - vector8.y)));    
    }
}

void DrawESP(PoisonModzEsp esp, int screenWidth, int screenHeight) {
    esp.DrawText(color, "ESP MADE BY EHROM TJ", Vector2(screenWidth / 2, screenHeight/1.2), 25);
    void *player = playerlate;
        
if(ESP) {
//Code Goes Inside it
    if (espManager->enemies->empty()) {
        return;
    }

    for (int i = 0; i < espManager->enemies->size(); i++) {
        //Code Goes Here
        void *Player = (*espManager->enemies)[i]->object;
        if (PlayerAlive(Player)) {
            //Code Goes Here
                Color clr;
                clr.a = 255;
                Rect rect;
                Vector3 PlayerPos = GetPlayerLocation(Player);
                void *Cam = get_camera();
                Vector3 PosNew = {0.f, 0.f, 0.f};
                PosNew = WorldToScreenPoint(Cam, PlayerPos);
                if (PosNew.z < 1.f) continue;
                Vector3 Origin;
                Origin = PlayerPos;
                Origin.y += 0.7f;
                float posnum = 10 * 0.1; /******* Hight slider * 0.1 ********/
                float posnum2 = 10 * 0.1;/******* Size slider * 0.1 ********/
                Origin.y += posnum;
                Vector3 BoxPosNew = {0.f, 0.f, 0.f};
                BoxPosNew = WorldToScreenPoint(Cam, Origin);
                float Hight =
                abs(BoxPosNew.y - PosNew.y) * (posnum2 / posnum), Width = Hight * 0.6f;

                rect = Rect(BoxPosNew.x - Width / 2.f,
                            screenHeight - BoxPosNew.y,
                            Width, Hight
                );
                
            Vector2 DrawFrom = Vector2(screenWidth /2 , screenHeight);
            Vector2 DrawTo = Vector2((screenWidth- (screenWidth - BoxPosNew.x)) + 5, (screenHeight - BoxPosNew.y));
            Vector2 To = Vector2((screenWidth- (screenWidth - BoxPosNew.x)) + 5, (screenHeight - BoxPosNew.y - 10.0f));
            Vector2 To2 = Vector2((screenWidth- (screenWidth - BoxPosNew.x)) + 5, (screenHeight - BoxPosNew.y + 10.0f));
            Vector2 LineTop = Vector2(screenWidth / 2, 0);
            Vector2 LineCenter = Vector2(screenWidth / 2, screenHeight / 2);
            Vector2 LineBottom = Vector2(screenWidth / 2, screenHeight) / 1;
            
            int MaxHealth = GetMaxHealth(Player);
            int CurHealth = GetPlayerHealth(Player);  
            float Tamanho = 0.0f;
            
            if (ESPLine) {
            if (Setting.PositionLine == 0) {
                esp.DrawLine(color, thickness, LineTop, DrawTo);
                }
            if (Setting.PositionLine == 1) {
                esp.DrawLine(color, thickness, LineCenter, DrawTo);
                }
            if (Setting.PositionLine == 2) {
                esp.DrawLine(color, thickness, LineBottom, DrawTo);
                }
            }
            
            if(ESPBox) {
               esp.DrawBox(color, thickness, rect);
            }
            
            if (ESPCorner) {
                float Height = abs(BoxPosNew.y - PosNew.y) * (posnum2 / posnum);
                float Width = Height * 0.6f;
                esp.DrawBox4Line(BoxPosNew.x - (Width / 2), (screenHeight - BoxPosNew.y - 10.0f), Width, Height + Tamanho,color,thickness);
            }   
            
            if(ESPHealth) {
               esp.DrawHorizontalHealthBar(Vector2((screenWidth - (screenWidth - PosNew.x)), (screenHeight - PosNew.y + 50.5f)), 50, MaxHealth, CurHealth);
            }
            
            if(ESP3d) {
               Draw3dBox(esp, color, PlayerPos, Cam, screenHeight, screenWidth);
            }
            
            if(ESPDistance) {
                int distance = (int) Vector2::Distance(Vector2(screenWidth , screenHeight), Vector2(BoxPosNew.x, BoxPosNew.y));
                std::string distances;
                distances = float_to_string(distance / 100);
                esp.DrawText(color,  ("(" +distances+ ")").c_str(), Vector2((screenWidth - (screenWidth - BoxPosNew.x)), (screenHeight - BoxPosNew.y - 10.0f)), playerTextSize);
            }               
    
      
            
            if(ESPCrossHair) {
               esp.DrawCrosshair( Color::Red(), Vector2(screenWidth / 2, screenHeight/2), CrossSize);
            }
            
            if(ESPObject){
               std::string Allplayers;
               Allplayers += "Players Alive ☠︎︎: ";
               Allplayers += std::to_string((int32_t) espManager->enemies->size());
               esp.DrawText(color, Allplayers.c_str(), Vector2(screenWidth / 2 ,screenHeight / 5), 35.0f);     
            }  
 
        } else {
            espManager->removeEnemyGivenObject(Player);
        }
    }
}
}


extern "C"
JNIEXPORT void JNICALL
Java_JusticeRivals3_ModMenuBY_Ehromtj_MenuV5_DrawOn(JNIEnv *env, jclass type, jobject espView, jobject canvas) {
                es = PoisonModzEsp(env, espView, canvas);
    if (es.isValid()){
        DrawESP(es, es.getWidth(), es.getHeight());
    }
}


bool (*IsLocal)(void* player);
void (*set_rotation)(void *player, Quaternion rotation);

void *isCarSize = NULL;
void (*old_CarUpdate)(void *player);
void CarUpdate(void *player) {
    if (player != NULL) {
        old_CarUpdate(player);
        isCarSize = player;
    }
}

void *enemyPlayer = NULL;
void (*old_espUpdate)(void *player);
void espUpdate(void *player) {
    if (player != NULL) {
        old_espUpdate(player);
        enemyPlayer = player;
    }
    if(enemyPlayer) {
       if(isLocalPlayerSize) {   
          SetLocalScale(getTransform(enemyPlayer), Vector3(isLocalPlayerSize, isLocalPlayerSize, isLocalPlayerSize));
    } else {
          SetLocalScale(getTransform(enemyPlayer), Vector3(1, 1, 1));
    }
    } else {
    enemyPlayer = NULL;
    return;
    }
    
	if(isCarSize) {
       if(isLocalCarSize) {   
          SetLocalScale(getTransform(isCarSize), Vector3(isLocalCarSize, isLocalCarSize, isLocalCarSize));
    } else {
          SetLocalScale(getTransform(isCarSize), Vector3(1, 1, 1));
    }
    } else {
    isCarSize = NULL;
    return;
    }

	
    if (ESP) {
        if (enemyPlayer) {
            espManager->tryAddEnemy(player);
        }
	
    }
}






#define targetLibName OBFUSCATE("libil2cpp.so")
void *hack_thread(void *) {
    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap("libil2cpp.so");
        sleep(1);
    } while (!il2cppMap.isValid() && mlovinit());
    setShader("_OFF");
    LogShaders();
    Wallhack();
    espManager = new ESPManager();
    do {
        sleep(1);
    } while (!isLibraryLoaded(targetLibName));
    LOGI(OBFUSCATE("%s has been loaded"), (const char *) targetLibName);

#if defined(__aarch64__) 
    
#else 
 
//  public class VehicleInfo -> private void FixedUpdate() { }
  MSHookFunction((void *)getAbsoluteAddress("libil2cpp.so", 0x12D1AA4), (void *) CarUpdate, (void **) &old_CarUpdate);
 
  // public class AICharacterFixedUpdate -> private void FixedUpdate() { }
  MSHookFunction((void *)getAbsoluteAddress("libil2cpp.so", 0x14AB350), (void *) espUpdate, (void **) &old_espUpdate);
 
 // public class Transform -> private void set_localScale_Injected(ref Vector3 value) { }
  SetLocalScale = (void (*)(void*, Vector3))getAbsoluteAddress(targetLibName, 0xB281F0);
     
    

	
    LOGI(OBFUSCATE("Done"));
#endif

    return NULL;
}

extern "C" {
JNIEXPORT jobjectArray
JNICALL
Java_JusticeRivals3_ModMenuBY_Ehromtj_MenuV5_getFeatureList(JNIEnv *env, jobject context) {
    jobjectArray ret;
    MakeToast(env, context, OBFUSCATE("MENU MADE BY EHROM TJ"), Toast::LENGTH_LONG);

    const char *features[] = {
    

            OBFUSCATE("Collapse_Esp Menu"),
            OBFUSCATE("0_CollapseAdd_Toggle_Enable ESP Player"), 
            OBFUSCATE("1_CollapseAdd_Toggle_ESP Line"), 
			OBFUSCATE("150_CollapseAdd_Toggle_ESP Health"), 
			OBFUSCATE("4_CollapseAdd_RadioButton_ESP Position Line_Top,Center,Bottom"), //4 Case
            OBFUSCATE("3_CollapseAdd_Toggle_ESP Distance"), 
            OBFUSCATE("2_CollapseAdd_RadioButton_ESP Box Style_Box,3D Box,Corner"), 
            OBFUSCATE("5_CollapseAdd_Toggle_ESP CrossHair"), 
            OBFUSCATE("6_CollapseAdd_Toggle_ESP Total Players"), 
            OBFUSCATE("7_CollapseAdd_SeekBar_Width Size_0_25"), 
            OBFUSCATE("8_CollapseAdd_SeekBar_Text Size_0_25"), 
            OBFUSCATE("9_CollapseAdd_SeekBar_CrossHair Size_0_120"),
            OBFUSCATE("120_CollapseAdd_SeekBar_Esp Color_0_7"), 
       
            
		    OBFUSCATE("Collapse_Size Menu"),
			OBFUSCATE("131_CollapseAdd_SeekBar_Player Size_0_100"), 
         	OBFUSCATE("132_CollapseAdd_SeekBar_Car Size_0_100"),
			
            OBFUSCATE("Collapse_Chams Menu"), //Not Counted  
			OBFUSCATE("154_CollapseAdd_Spinner_Select Chams Shader_None (Default),Shader 1,Shader 2,Shader 3,Shader 3,Shader 4,Shader 5,Shader 6,Shader 7,Shader 8,Shader 9,Shader 10,Shader 11,Shader 12,Shader 13,Shader 14,Shader 15, Shader 16, Shader 17, Shader 18, Shader 19"),
			OBFUSCATE("11_CollapseAdd_CheckBox_Default Chams"), //11 Case  
			OBFUSCATE("12_CollapseAdd_CheckBox_Wireframe Chams"), //12 Case  
			OBFUSCATE("13_CollapseAdd_CheckBox_Glow Chams"), //13 Case  
			OBFUSCATE("14_CollapseAdd_CheckBox_Outline Chams"), //14 Case  
			OBFUSCATE("15_CollapseAdd_CheckBox_Rainbow Chams"), //15 Case  
			OBFUSCATE("16_CollapseAdd_SeekBar_Line Width_0_10"), //16 Case  
			OBFUSCATE("17_CollapseAdd_SeekBar_Color Red_0_255"), //17 Case  
			OBFUSCATE("18_CollapseAdd_SeekBar_Color Green_0_255"), //18 Case  
			OBFUSCATE("19_CollapseAdd_SeekBar_Color Blue_0_255"), //19 Case
			OBFUSCATE("159_CollapseAdd_SeekBar_Map Ui Size_0_100"),
            
			
			
			
            };
    //Now you dont have to manually update the number everytime;
    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass(OBFUSCATE("java/lang/String")),
                                env->NewStringUTF(""));

    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));

    pthread_t ptid;
    pthread_create(&ptid, NULL, antiLeech, NULL);

    return (ret);
}

JNIEXPORT void JNICALL
Java_JusticeRivals3_ModMenuBY_Ehromtj_Preferences_Changes(JNIEnv *env, jclass clazz, jobject obj,
                                        jint featNum, jstring featName, jint value,
                                        jboolean boolean, jstring str) {

    LOGD(OBFUSCATE("Feature name: %d - %s | Value: = %d | Bool: = %d | Text: = %s"), featNum,
         env->GetStringUTFChars(featName, 0), value,
         boolean, str != NULL ? env->GetStringUTFChars(str, 0) : "");

    //BE CAREFUL NOT TO ACCIDENTLY REMOVE break;

    switch (featNum) {
        case 0:
            ESP = boolean;
            break;      
        case 1:
            ESPLine = boolean;
            break;  
        case 2:
            switch (value) {
                case 0:
                    ESPBox = true;
                    ESP3d = false;
                    ESPCorner = false;
                    break;
                case 1:
                    ESP3d = true;
                    ESPBox = false;
                    ESPCorner = false;
                    break;       
                case 2: 
                    ESPCorner = true;
                    ESPBox = false;
                    ESP3d = false;
            }
            break;    
        case 3:
            ESPDistance = boolean;
            break;  
        case 4:
            Setting.PositionLine = value;
            break;  
        case 5:
            ESPCrossHair = boolean;
            break;
        case 6:
            ESPObject = boolean;
            break;       
        case 7:
            thickness = value;
            break;
        case 8:
            if (value > 0) {
                playerTextSize = value*5.0;
            } else {
                playerTextSize = 5.0;
            } 
            break;  
        case 9:
            if (value > 0) {
                CrossSize = value*5;
            } else {
                CrossSize = 5;
            } 
            break;  
			
			
			case 11:
            SetWallhack(boolean); 
            break;    
        case 12:           
            SetWallhackW(boolean);
            break;    
        case 13:          
            SetWallhackG(boolean);
            break;     
        case 14:          
            SetWallhackO(boolean);
            break;    
        case 15:     
            SetRainbow1(boolean);
            break;
        case 16:                
            SetW(value);
            break;    
        case 17:            
            SetR(value);
            break;
        case 18:                      
            SetG(value);
            break;    
        case 19:           
            SetB(value);
			break;    
			
        case 120:
            switch (value) {
                case 0:
                    color = Color::Red(255);
                    break;
                case 1:
                    color = Color::Green(255);
                    break;
                case 2:
                    color = Color::Cyan(255);
                    break; 
                case 3:
                    color = Color::Black(255);
                    break;
                case 4:
                    color = Color::White(255);
                    break;
                case 5:
                    color = Color::Blue(255);
                    break; 
                case 6:
                    color = Color::Yellow(255);
                    break;
                case 7:
                    color = Color::Magenta(255);
                    break;  
            }
            break;        
            
        
        case 131:
        if (value >= 1 && value <= 9){
        isLocalPlayerSize = value * 0.1;
        }else if (value >= 10 && value <= 150){
        isLocalPlayerSize = value * 0.5;
        }else if(value == 0){
        isLocalPlayerSize = value;
        } break;
			
		case 132:
        if (value >= 1 && value <= 9){
        isLocalCarSize = value * 0.1;
        }else if (value >= 10 && value <= 150){
        isLocalCarSize = value * 0.5;
        }else if(value == 0){
        isLocalCarSize = value;
        }
			break;	
		
	case 154: 
            switch (value) {
                case 0:
                    setShader("Off");
                    break;
                case 1:
                    setShader("_MainTex");
                    break;
                case 2:
                    setShader("_MainTex_ST");
                    break; 
                case 3:
                    setShader("hlslcc_mtx4x4unity_MatrixVP[0]");
                    break;
                case 4:
                    setShader("hlslcc_mtx4x4unity_WorldToObject[0]");
                    break;
				case 5:
                    setShader("hlslcc_mtx4x4unity_ObjectToWorld[0]");
                    break;
                case 6:
                    setShader("_Color");
                    break;
                case 7:
                    setShader("_MainTex_ST");
                    break; 
                case 8:
                    setShader("hlslcc_mtx4x4unity_MatrixVP[0]");
                    break;
                case 9:
                    setShader("hlslcc_mtx4x4unity_WorldToObject[0]");
                    break;
				case 10:
                    setShader("_WorldSpaceCameraPos[0]");
                    break;
                case 11:
                    setShader("unity_FogParams");
                    break;
                case 12:
                    setShader("hlslcc_mtx4x4unity_MatrixV[0]");// Body Shaders
                    break; 
                case 13:
                    setShader("_EmisColor");
                    break;
                case 14:
                    setShader("_Cube");
                    break;
					//Тест другие
				case 15:
                    setShader("_BumpMap");
                    break;
                case 16:
                    setShader("unity_WorldTransformParams");
                    break; 
                case 17:
                    setShader("_Shininess");
                    break;
                case 18:
                    setShader("unity_Lightmap");
                    break;
				case 19:
                    setShader("_MainTex2_ST");
                    break;	
					
					
                          
            }
            break;  
			
	

// BYPASS ☠️

	case 999999999:
PATCH_SWITCH("0x131E1D4","1EFF2FE1",boolean);//SendAdminUnbanPlayer
PATCH_SWITCH("0x131DF48","1EFF2FE1",boolean);//SendAdminBanCommand
PATCH_SWITCH("0x107B238","1EFF2FE1",boolean);//UnbanConfirm
PATCH_SWITCH("0x107B134","1EFF2FE1",boolean);//RemoveBannedPlayer
PATCH_SWITCH("0x107AE88","1EFF2FE1",boolean);//BanConfirm() { }
PATCH_SWITCH("0x107B910","1EFF2FE1",boolean);//DeleteVehicleConfirm() { }
PATCH_SWITCH("0x109E164","1EFF2FE1",boolean);//CanViewBannerAd
PATCH_SWITCH("0x10F9284","1EFF2FE1",boolean);//DestroyBannerAd() { }
PATCH_SWITCH("0x10F92FC","1EFF2FE1",boolean);//HideBannerAd
PATCH_SWITCH("0xF292A8","1EFF2FE1",boolean);//DestroyBannerView() { }
PATCH_SWITCH("0xF2920C","1EFF2FE1",boolean);//HideBannerView() { }
PATCH_SWITCH("0xF29060","1EFF2FE1",boolean);//LoadAd(AdRequest request) { }
PATCH_SWITCH("0xF2848C","1EFF2FE1",boolean);//CreateBannerView(string adUnitId, AdSize adSize, AdPosition position) { }
PATCH_SWITCH("0xF28E00","1EFF2FE1",boolean);//CreateBannerView(string adUnitId, AdSize adSize, int x, int y) { }
PATCH_SWITCH("0x91E7AC","1EFF2FE1",boolean);//DeleteFile(char* path, out MonoIOError error) { }
PATCH_SWITCH("0x91E7B0","1EFF2FE1",boolean);//DeleteFile(string path, out MonoIOError error) { }
PATCH_SWITCH("0xD88FF0","1EFF2FE1",boolean);//IsDeletedMember(int index) { }
PATCH_SWITCH("0xD88C60","1EFF2FE1",boolean);//TryDeleteValue(object indexClass, int index, string name, bool ignoreCase, object deleteValue) { }
PATCH_SWITCH("0x5B85FC","1EFF2FE1",boolean);//DeleteKey(string key) { }
PATCH_SWITCH("0xBCB93C","1EFF2FE1",boolean);//CascadeDelete(DataRow row) { }
PATCH_SWITCH("0xC40F24","1EFF2FE1",boolean);//DeleteRecord(int recordIndex) { }
PATCH_SWITCH("0xC415BC","1EFF2FE1",boolean);//DeleteRecord(int recordIndex, bool fireEvent) { }
PATCH_SWITCH("0xF65B68","1EFF2FE1",boolean);//DeleteLineBack() { }
PATCH_SWITCH("0xF65EB0","1EFF2FE1",boolean);//DeleteWordBack() { }
PATCH_SWITCH("0xF66054","1EFF2FE1",boolean);//DeleteWordForward() { }
PATCH_SWITCH("0xF66370","1EFF2FE1",boolean);//Delete() { }
PATCH_SWITCH("0xF65C8C","1EFF2FE1",boolean);//DeleteSelection() { }
PATCH_SWITCH("0x10561E8","1EFF2FE1",boolean);//DeleteCharacter() { }
PATCH_SWITCH("0x106F1E8","1EFF2FE1",boolean);//DeleteFromMemory(AICharacter OCIAMHINOON) { }
PATCH_SWITCH("0x10D0FC8","1EFF2FE1",boolean);//DeletePlayerCharacter() { }
PATCH_SWITCH("0x139FA68","1EFF2FE1",boolean);//DeletePlayerCharacters() { }
PATCH_SWITCH("0xFFA3B0","1EFF2FE1",boolean);//DeletePlayerAccount() { }
PATCH_SWITCH("0x107B910","1EFF2FE1",boolean);//DeleteVehicleConfirm() { }
PATCH_SWITCH("0x131E3C4","1EFF2FE1",boolean);//SendAdminDeleteVehicle(int DCDOKLPBNEC) { }
PATCH_SWITCH("0x10D0520","1EFF2FE1",boolean);//DeletePose() { }
PATCH_SWITCH("0x14CBC74","1EFF2FE1",boolean);//ShowAccountDeleteConfirmDialog() { }
PATCH_SWITCH("0x14CBD18","1EFF2FE1",boolean);//CancelAccountDelete() { }
PATCH_SWITCH("0x14CBDBC","1EFF2FE1",boolean);//ConfirmAccountDelete() { }
PATCH_SWITCH("0x1CEF948","1EFF2FE1",boolean);//ForceCheck() { }
PATCH_SWITCH("0x1CEF988","1EFF2FE1",boolean);//IsReadyForForceCheck() { }
PATCH_SWITCH("0x106DAFC","1EFF2FE1",boolean);//ForceStreamOutAllActors() { }
PATCH_SWITCH("0xFEDDBC","1EFF2FE1",boolean);//ForceMultiplayerLogout() { }
PATCH_SWITCH("0x1CE07F4","1EFF2FE1",boolean);//ForceStop() { }
break;
	
	
	
	
	
    }
}
}

__attribute__((constructor))
void lib_main() {
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);
}

