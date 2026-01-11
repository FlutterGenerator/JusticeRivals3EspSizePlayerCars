#ifndef PoisonModzEsp_H
#define PoisonModzEsp_H

#include <jni.h>
class PoisonModzEsp {
private:
    JNIEnv *_env;
    jobject _cvsView;
    jobject _cvs;

public:
    PoisonModzEsp() {
        _env = nullptr;
        _cvsView = nullptr;
        _cvs = nullptr;
    }

    PoisonModzEsp(JNIEnv *env, jobject cvsView, jobject cvs) {
        this->_env = env;
        this->_cvsView = cvsView;
        this->_cvs = cvs;
    }

    bool isValid() const {
        return (_env != nullptr && _cvsView != nullptr && _cvs != nullptr);
    }

    int getWidth() const {
        if (isValid()) {
            jclass canvas = _env->GetObjectClass(_cvs);
            jmethodID width = _env->GetMethodID(canvas, "getWidth", "()I");
            return _env->CallIntMethod(_cvs, width);
        }
        return 0;
    }

    int getHeight() const {
        if (isValid()) {
            jclass canvas = _env->GetObjectClass(_cvs);
            jmethodID width = _env->GetMethodID(canvas, "getHeight", "()I");
            return _env->CallIntMethod(_cvs, width);
        }
        return 0;
    }

    void DrawLine(Color color, float thickness, Vector2 start, Vector2 end) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawline = _env->GetMethodID(canvasView, "DrawLine",
                                                   "(Landroid/graphics/Canvas;IIIIFFFFF)V");
            _env->CallVoidMethod(_cvsView, drawline, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 thickness,
                                 start.X, start.Y, end.X, end.Y);
        }
    }

    void DrawText(Color color, const char *txt, Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawText",
                                                   "(Landroid/graphics/Canvas;IIIILjava/lang/String;FFF)V");
            _env->CallVoidMethod(_cvsView, drawtext, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 _env->NewStringUTF(txt), pos.X, pos.Y, size);
        }
    }

    void DrawFilledCircle(Color color, Vector2 pos, float radius) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawfilledcircle = _env->GetMethodID(canvasView, "DrawFilledCircle",
                                                           "(Landroid/graphics/Canvas;IIIIFFF)V");
            _env->CallVoidMethod(_cvsView, drawfilledcircle, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b, pos.X, pos.Y, radius);
        }
    }
	
    void DrawBox4Line(int x, int y, int w, int h, Color color, float thickness) {
     int iw = w / 4;
     int ih = h / 4;

     DrawLine(color,thickness,Vector2(x, y),Vector2(x + iw, y));
     DrawLine(color,thickness,Vector2(x + w - iw, y),Vector2(x + w, y));
     DrawLine(color,thickness,Vector2(x, y),Vector2(x, y + ih));
     DrawLine(color,thickness,Vector2(x + w - 1, y),Vector2(x + w - 1, y + ih));

     DrawLine(color,thickness,Vector2(x, y + h),Vector2(x + iw, y + h));
     DrawLine(color,thickness,Vector2(x + w - iw, y + h),Vector2(x + w, y + h));
     DrawLine(color,thickness,Vector2(x, y + h - ih), Vector2(x, y + h));
     DrawLine(color,thickness,Vector2(x + w - 1, y + h - ih), Vector2(x + w - 1, y + h));
   }
   
    void DrawBox(Color color, float stroke, Rect rect) {
        Vector2 v1 = Vector2(rect.x, rect.y);
        Vector2 v2 = Vector2(rect.x + rect.w, rect.y);
        Vector2 v3 = Vector2(rect.x + rect.w, rect.y + rect.h);
        Vector2 v4 = Vector2(rect.x, rect.y + rect.h);

        DrawLine(color, stroke, v1, v2);
        DrawLine(color, stroke, v2, v3);
        DrawLine(color, stroke, v3, v4);
        DrawLine(color, stroke, v4, v1);
    }

    void DrawHorizontalHealthBar(Vector2 screenPos, float width, float maxHealth, float currentHealth) {
        screenPos -= Vector2(0.0f, 8.0f);
        DrawBox(Color(0, 0, 0, 255), 3, Rect(screenPos.X, screenPos.Y, width + 2, 5.0f));
        screenPos += Vector2(1.0f, 1.0f);
        Color clr = Color(0, 255, 0, 255);
        float hpWidth = (currentHealth * width) / maxHealth;
        if (currentHealth <= (maxHealth * 0.6)) {
            clr = Color(255, 255, 0, 255);
        }
        if (currentHealth < (maxHealth * 0.3)) {
            clr = Color(255, 0, 0, 255);
        }
        DrawBox(clr, 3, Rect(screenPos.X, screenPos.Y, hpWidth, 3.0f));
    }

    void DrawCrosshair(Color clr, Vector2 center, float size = 20) {
        float x = center.X - (size / 2.0f);
        float y = center.Y - (size / 2.0f);
        DrawLine(clr, 3, Vector2(x, center.Y), Vector2(x + size, center.Y));
        DrawLine(clr, 3, Vector2(center.X, y), Vector2(center.X, y + size));
    }
};

#endif

