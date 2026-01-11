package JusticeRivals3.ModMenuBY.Ehromtj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class ColorPickerView extends View {
  private int alphaVal = 255;
  private float[] colorHSV = new float[] {0.0f, 0.0f, 1.0f};
  private Paint colorPointerPaint;
  private int colorPointerRad = this.dpToPixels(5);
  private Bitmap colorWheelBitmap;
  private Paint colorWheelPaint;
  private int colorWheelRadius;
  ColorChangedListener mColorChangedListener;
  private int padding = this.dpToPixels(10);

  public ColorPickerView(Context context) {
    super(context);
    this.init();
  }

  public ColorPickerView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    this.init();
  }

  public ColorPickerView(Context context, AttributeSet attributeSet, int n) {
    super(context, attributeSet, n);
    this.init();
  }

  private Bitmap createColorWheelBitmap(int n) {
    if (n < 1) {
      return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
    }
    Bitmap bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);
    int[] arrn = new int[13];
    float[] arrf = new float[] {0.0f, 1.0f, 1.0f};
    for (int i = 0; i < arrn.length; ++i) {
      arrf[0] = (180 + i * 30) % 360;
      arrn[i] = Color.HSVToColor(arrf);
    }
    arrn[12] = arrn[0];
    float f2 = n / 2;
    SweepGradient sweepGradient = new SweepGradient(f2, f2, arrn, null);
    RadialGradient radialGradient =
        new RadialGradient(
            f2, f2, (float) this.colorWheelRadius, -1, 16777215, Shader.TileMode.CLAMP);
    ComposeShader composeShader =
        new ComposeShader(
            (Shader) sweepGradient, (Shader) radialGradient, PorterDuff.Mode.SRC_OVER);
    this.colorWheelPaint.setShader((Shader) composeShader);
    new Canvas(bitmap).drawCircle(f2, f2, (float) this.colorWheelRadius, this.colorWheelPaint);
    return bitmap;
  }

  private void init() {

    this.colorPointerPaint = new Paint(1);
    this.colorPointerPaint.setDither(true);
    this.colorPointerPaint.setStyle(Paint.Style.STROKE);
    this.colorPointerPaint.setStrokeWidth((float) this.dpToPixels(2));
    this.colorPointerPaint.setARGB(255, 0, 0, 0);
    this.colorWheelPaint = new Paint();
    this.colorWheelPaint.setAntiAlias(true);
    this.colorWheelPaint.setDither(true);
    this.colorWheelPaint.setFilterBitmap(true);
  }

  int dpToPixels(int n) {
    Resources resources = this.getResources();
    return (int)
        TypedValue.applyDimension(
            1, (float) n, resources.getDisplayMetrics());
  }

  public int getAlphaVal() {
    return this.alphaVal;
  }

  public int getColor() {
    return Color.HSVToColor(this.alphaVal, this.colorHSV);
  }

  public float[] getColorHSV() {
    return this.colorHSV;
  }

  public float getSaturation() {
    return this.colorHSV[1];
  }

  public float getValue() {
    return this.colorHSV[2];
  }

  @SuppressLint(value = {"DrawAllocation"})
  protected void onDraw(Canvas canvas) {
    if (this.colorWheelBitmap.getWidth() < 4) {
      return;
    }
    float f2 = (float) this.getWidth() / 2.0f;
    float f3 = (float) this.getHeight() / 2.0f;
    Bitmap bitmap = this.colorWheelBitmap;
    int n = this.colorWheelRadius;
    canvas.drawBitmap(bitmap, f2 - (float) n, f3 - (float) n, this.colorWheelPaint);
    canvas.drawCircle(f2, f3, (float) this.colorWheelRadius, this.colorPointerPaint);
    double d2 = (float) Math.toRadians((double) this.colorHSV[0]);
    double d3 = -Math.cos(d2);
    double d4 = this.colorHSV[1];
    Double.isNaN(d4);
    double d5 = d3 * d4;
    double d6 = this.colorWheelRadius;
    Double.isNaN(d6);
    double d7 = d5 * d6;
    double d8 = f2;
    Double.isNaN(d8);
    float f4 = (float) (d7 + d8);
    double d9 = -Math.sin(d2);
    double d10 = this.colorHSV[1];
    Double.isNaN(d10);
    double d11 = d9 * d10;
    double d12 = this.colorWheelRadius;
    Double.isNaN(d12);
    double d13 = d11 * d12;
    double d14 = f3;
    Double.isNaN(d14);
    canvas.drawCircle(
        f4, (float) (d13 + d14), (float) this.colorPointerRad, this.colorPointerPaint);
  }

  protected void onMeasure(int n, int n2) {
    int n3 =
        Math.min((int) View.MeasureSpec.getSize(n), (int) View.MeasureSpec.getSize(n2));
    this.setMeasuredDimension(n3, n3);
  }

  protected void onSizeChanged(int n, int n2, int n3, int n4) {
    this.colorWheelRadius = n / 2 - this.padding;
    this.colorWheelBitmap = this.createColorWheelBitmap(2 * this.colorWheelRadius);
  }

  public boolean onTouchEvent(MotionEvent motionEvent) {
    float f2;
    int n = motionEvent.getAction();
    if (n != 0 && n != 2) {
      return super.onTouchEvent(motionEvent);
    }
    float f3 = motionEvent.getX();
    float f4 = motionEvent.getY();
    float f5 = f3 - (float) (this.getWidth() / 2);
    double d2 = Math.sqrt((double) (f5 * f5 + (f2 = f4 - (float) (this.getHeight() / 2)) * f2));
    if (d2 <= (double) this.colorWheelRadius) {
      this.colorHSV[0] =
          (float) (180.0 + Math.toDegrees((double) Math.atan2((double) f2, (double) f5)));
      float[] arrf = this.colorHSV;
      double d3 = this.colorWheelRadius;
      Double.isNaN(d3);
      arrf[1] = Math.max(0.0f, (float) Math.min(1.0f, ((float) (d2 / d3))));
      ColorChangedListener colorChangedListener = this.mColorChangedListener;
      if (colorChangedListener != null) {
        colorChangedListener.colorChange();
      }
      this.invalidate();
    }
    return true;
  }

  public void setAlphaVal(int n) {
    this.alphaVal = n;
    ColorChangedListener colorChangedListener = this.mColorChangedListener;
    if (colorChangedListener != null) {
      colorChangedListener.colorChange();
    }
    this.invalidate();
  }

  public void setBlue(int n) {
    int n2 = this.getColor();
    this.setColor(Color.rgb((int) Color.red(n2), (int) Color.green(n2), n));
  }

  public void setColor(int n) {
    this.setColor(n, this.alphaVal);
  }

  public void setColor(int n, int n2) {
    Color.colorToHSV(n, this.colorHSV);
    this.setAlphaVal(n2);
    ColorChangedListener colorChangedListener = this.mColorChangedListener;
    if (colorChangedListener != null) {
      colorChangedListener.colorChange();
    }
    this.invalidate();
  }

  public void setGreen(int n) {
    int n2 = this.getColor();
    this.setColor(Color.rgb((int) Color.red(n2), n, (int) Color.blue(n2)));
  }

  public void setRed(int n) {
    int n2 = this.getColor();
    this.setColor(Color.rgb(n, (int) Color.green(n2), (int) Color.blue(n2)));
  }

  public void setSaturation(float f2) {
    this.colorHSV[1] = f2;
    ColorChangedListener colorChangedListener = this.mColorChangedListener;
    if (colorChangedListener != null) {
      colorChangedListener.colorChange();
    }
    this.invalidate();
  }

  public void setValue(float f2) {
    this.colorHSV[2] = f2;
    ColorChangedListener colorChangedListener = this.mColorChangedListener;
    if (colorChangedListener != null) {
      colorChangedListener.colorChange();
    }
    this.invalidate();
  }

  public void setOnColorChangedListener(ColorChangedListener colorChangedListener) {
    this.mColorChangedListener = colorChangedListener;
  }

  public interface ColorChangedListener {
    public void colorChange();
  }
}

class ColorPickerImgui extends View {
  private Paint colorPaint;
  private Paint huePaint;
  private Paint circlePaint;
  private Paint hueCirclePaint;
  private Paint hueCircleOutlinePaint;
  private float[] hsv = {0, 1, 1}; // Default HSV values
  private Bitmap colorBitmap;
  private int selectedColor = Color.RED;
  private OnColorChangedListener colorChangedListener;
  private int width, height;
  private int hueSliderWidth = 100; // Width of the hue slider
  private int margin = 20; // Margin between color picker and hue slider
  private float circleX = -1, circleY = -1; // Position of the selected color circle
  private float hueCircleY = -1; // Position of the hue circle
  private int hueSliderX; // X position of the hue slider rectangle
  private float triangleSize = 20; // Default triangle size

  public ColorPickerImgui(Context context) {
    super(context);
    init();
  }

  private void init() {
    colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    huePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    hueCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    hueCircleOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    circlePaint.setStyle(Paint.Style.STROKE);
    circlePaint.setStrokeWidth(5);
    circlePaint.setColor(Color.BLACK);

    hueCirclePaint.setStyle(Paint.Style.FILL);
    hueCircleOutlinePaint.setStyle(Paint.Style.STROKE);
    hueCircleOutlinePaint.setColor(Color.BLACK);
    hueCircleOutlinePaint.setStrokeWidth(3);
  }

  // New method to set the triangle size
  public void setTriangleSize(float size) {
    this.triangleSize = size;
    invalidate(); // Refresh the view to apply the new triangle size
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    width = w;
    height = h;
    hueSliderX = width - hueSliderWidth;

    createColorGrid();

    int colorGridWidth = width - hueSliderWidth - margin;
    circleX = colorGridWidth / 2f;
    circleY = height / 2f;
    hueCircleY = height / 2f;

    updateColorAndCirclePosition();

    if (colorChangedListener != null) {
      colorChangedListener.onColorChanged(selectedColor);
    }
  }

  private void createColorGrid() {
    int colorGridWidth = width - hueSliderWidth - margin;

    if (colorBitmap == null
        || colorBitmap.getWidth() != colorGridWidth
        || colorBitmap.getHeight() != height) {
      colorBitmap = Bitmap.createBitmap(colorGridWidth, height, Bitmap.Config.ARGB_8888);
    }
    Canvas tempCanvas = new Canvas(colorBitmap);
    for (int y = 0; y < height; y++) {
      float val = 1 - (float) y / height;
      int startColor = Color.HSVToColor(new float[] {hsv[0], 0, val});
      int endColor = Color.HSVToColor(new float[] {hsv[0], 1, val});
      LinearGradient gradient =
          new LinearGradient(0, 0, colorGridWidth, 0, startColor, endColor, Shader.TileMode.CLAMP);
      Paint paint = new Paint();
      paint.setShader(gradient);
      tempCanvas.drawRect(0, y, colorGridWidth, y + 1, paint);
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (colorBitmap != null) {
      canvas.drawBitmap(colorBitmap, 0, 0, colorPaint);
    }

    for (int y = 0; y < height; y++) {
      float hue = 360f * y / height;
      huePaint.setColor(Color.HSVToColor(new float[] {hue, 1, 1}));
      canvas.drawRect(hueSliderX, y, hueSliderX + hueSliderWidth, y + 1, huePaint);
    }

    if (circleX != -1 && circleY != -1) {
      updateCirclePaintColor();
      canvas.drawCircle(circleX, circleY, 20, circlePaint);
    }

    // Draw the triangle indicator for hue with dynamic size
    drawTriangleIndicator(canvas, hueSliderX + hueSliderWidth / -2f, hueCircleY);
  }

  private void drawTriangleIndicator(Canvas canvas, float centerX, float centerY) {
    // Use dynamic triangle size
    float triangleWidth = triangleSize; // Width of the base
    float triangleHeight = triangleSize * 0.75f; // Height of the triangle (proportional)

    // Right triangle (▶)
    float rightTipX = centerX + triangleWidth; // Tip of the right triangle
    float rightTopY = centerY - triangleHeight / 2f; // Top of the triangle
    float rightBottomY = centerY + triangleHeight / 2f; // Bottom of the triangle

    Path rightTrianglePath = new Path();
    rightTrianglePath.moveTo(centerX, rightTopY); // Top point
    rightTrianglePath.lineTo(centerX, rightBottomY); // Bottom point
    rightTrianglePath.lineTo(rightTipX, centerY); // Tip point
    rightTrianglePath.close();

    Paint rightTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    rightTrianglePaint.setStyle(Paint.Style.FILL);
    rightTrianglePaint.setColor(Color.WHITE);
    canvas.drawPath(rightTrianglePath, rightTrianglePaint);
  }

  private void updateCirclePaintColor() {
    if (colorBitmap != null
        && circleX >= 0
        && circleY >= 0
        && circleX < colorBitmap.getWidth()
        && circleY < colorBitmap.getHeight()) {
      int pixel = colorBitmap.getPixel((int) circleX, (int) circleY);
      float luminance =
          0.299f * Color.red(pixel) + 0.587f * Color.green(pixel) + 0.114f * Color.blue(pixel);
      circlePaint.setColor(luminance < 128 ? Color.WHITE : Color.BLACK);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();

    int colorGridWidth = width - hueSliderWidth - margin;

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        if (x >= 0 && x < colorGridWidth && y >= 0 && y < height) {
          circleX = x;
          circleY = y;

          hsv[1] = x / colorGridWidth; // Saturation
          hsv[2] = 1 - y / height; // Value
          selectedColor = Color.HSVToColor(hsv);

          if (colorChangedListener != null) {
            colorChangedListener.onColorChanged(selectedColor);
          }

          updateCirclePaintColor();

          invalidate(
              (int) (circleX - 20),
              (int) (circleY - 20),
              (int) (circleX + 20),
              (int) (circleY + 20));
        }

        if (x >= hueSliderX && x <= hueSliderX + hueSliderWidth && y >= 0 && y < height) {
          hueCircleY = y;
          hsv[0] = 360f * y / height; // Hue
          selectedColor = Color.HSVToColor(hsv);

          createColorGrid();

          if (colorChangedListener != null) {
            colorChangedListener.onColorChanged(selectedColor);
          }

          updateCirclePaintColor();

          invalidate(hueSliderX, 0, hueSliderX + hueSliderWidth, height);
          invalidate(0, 0, colorGridWidth, height);
        }
        break;

      case MotionEvent.ACTION_UP:
        if (x >= hueSliderX && x <= hueSliderX + hueSliderWidth && y >= 0 && y < height) {
          hueCircleY = y;
          hsv[0] = 360f * y / height; // Hue
          selectedColor = Color.HSVToColor(hsv);

          createColorGrid();

          if (colorChangedListener != null) {
            colorChangedListener.onColorChanged(selectedColor);
          }

          updateCirclePaintColor();

          invalidate(hueSliderX, 0, hueSliderX + hueSliderWidth, height);
          invalidate(0, 0, colorGridWidth, height);
        }
        break;
    }

    return true;
  }

  public void setColor(int color) {
    float[] hsvColor = new float[3];
    Color.colorToHSV(color, hsvColor);

    hsv[0] = hsvColor[0];
    hsv[1] = hsvColor[1];
    hsv[2] = hsvColor[2];

    updateColorAndCirclePosition();
    updateCirclePaintColor(); // Ensure the circle color is updated

    if (colorChangedListener != null) {
      colorChangedListener.onColorChanged(selectedColor);
    }

    invalidate();
  }

  private void updateColorAndCirclePosition() {
    int colorGridWidth = width - hueSliderWidth - margin;
    float sat = hsv[1];
    float val = hsv[2];
    selectedColor = Color.HSVToColor(new float[] {hsv[0], sat, val});

    circleX = sat * colorGridWidth;
    circleY = (1 - val) * height;

    hueCircleY = (hsv[0] / 360f) * height;
  }

  public void setOnColorChangedListener(OnColorChangedListener listener) {
    this.colorChangedListener = listener;
  }

  public void setHueSliderWidth(int width) {
    this.hueSliderWidth = width;
    invalidate();
  }

  public void setMargin(int margin) {
    this.margin = margin;
    invalidate();
  }

  public interface OnColorChangedListener {
    void onColorChanged(int color);
  }
}

class CircularHSVSeekBar extends View {
  private Paint trackPaint;
  private Paint thumbPaint, thumbStrokePaint, selectorPaint;
  private Paint pickerCirclePaint;
  private RectF circleBounds;
  private float thumbAngle = 0f;
  private float thumbRadius = 40f;
  private float pickerRadius = 175f;
  private int currentColor = Color.RED;
  private int pickerColor = Color.RED;
  private Bitmap pickerBitmap;
  private Canvas pickerCanvas;
  private OnColorSelectedListener listener;
  private float selectorX, selectorY;
  private boolean isThumbTouched = false;
  private float centerX, centerY, radius;
  private float[] hsv = new float[3];

  public CircularHSVSeekBar(Context context) {
    super(context);
    init();
  }

  private void init() {
    trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    trackPaint.setStyle(Paint.Style.STROKE);
    trackPaint.setStrokeWidth(40);

    thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    thumbPaint.setStyle(Paint.Style.FILL);

    thumbStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    thumbStrokePaint.setStyle(Paint.Style.STROKE);
    thumbStrokePaint.setStrokeWidth(5);

    pickerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    pickerCirclePaint.setStyle(Paint.Style.STROKE);
    pickerCirclePaint.setStrokeWidth(5);

    selectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    selectorPaint.setStyle(Paint.Style.STROKE);
    selectorPaint.setStrokeWidth(5);

    circleBounds = new RectF();

    // Default HSV setup
    Color.colorToHSV(currentColor, hsv);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    centerX = w / 2f;
    centerY = h / 2f;
    radius = Math.min(w, h) / 2f - 40;
    circleBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    pickerBitmap =
        Bitmap.createBitmap(
            (int) pickerRadius * 2, (int) pickerRadius * 2, Bitmap.Config.ARGB_8888);
    pickerCanvas = new Canvas(pickerBitmap);

    drawPickerBitmap();
    setColor(currentColor); // Set the initial color and position
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // Draw color wheel track
    for (int i = 0; i < 360; i++) {
      trackPaint.setColor(Color.HSVToColor(new float[] {i, 1, 1}));
      canvas.drawArc(circleBounds, i - 90, 1, false, trackPaint);
    }

    // Draw color picker
    canvas.drawBitmap(pickerBitmap, centerX - pickerRadius, centerY - pickerRadius, null);
    canvas.drawCircle(centerX, centerY, pickerRadius, pickerCirclePaint);

    // Draw thumb (current color)
    float thumbX = centerX + radius * (float) Math.cos(Math.toRadians(thumbAngle - 90));
    float thumbY = centerY + radius * (float) Math.sin(Math.toRadians(thumbAngle - 90));
    thumbPaint.setColor(currentColor);
    canvas.drawCircle(thumbX, thumbY, thumbRadius, thumbPaint);
    canvas.drawCircle(thumbX, thumbY, thumbRadius, thumbStrokePaint);

    // Draw the color selector inside the picker bitmap
    canvas.drawCircle(selectorX, selectorY, 20, selectorPaint);
  }

  private void drawPickerBitmap() {
    int cx = pickerBitmap.getWidth() / 2;
    int cy = pickerBitmap.getHeight() / 2;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Create gradients for value and saturation
    LinearGradient valGradient =
        new LinearGradient(
            0, 0, 0, pickerRadius * 2, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
    LinearGradient satGradient =
        new LinearGradient(
            0,
            0,
            pickerRadius * 2,
            0,
            Color.WHITE,
            Color.HSVToColor(new float[] {hsv[0], 1f, 1f}),
            Shader.TileMode.CLAMP);

    // Combine the gradients
    ComposeShader merged = new ComposeShader(valGradient, satGradient, PorterDuff.Mode.MULTIPLY);
    paint.setShader(merged);

    // Clear the bitmap and draw the new color picker
    pickerCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    pickerCanvas.drawCircle(cx, cy, pickerRadius, paint);
    pickerCanvas.drawCircle(cx, cy, pickerRadius, pickerCirclePaint);
  }

  private int getColorForAngle(float angle) {
    float[] hsv = {angle, 1f, 1f};
    return Color.HSVToColor(hsv);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    float dx = x - centerX;
    float dy = y - centerY;
    float distanceFromCenter = (float) Math.sqrt(dx * dx + dy * dy);
    float angle = (float) Math.toDegrees(Math.atan2(dy, dx)) + 90;

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        if (distanceFromCenter >= radius - thumbRadius
            && distanceFromCenter <= radius + thumbRadius) {
          isThumbTouched = true;
          thumbAngle = normalizeAngle(angle);
          currentColor = getColorForAngle(thumbAngle);
          hsv[0] = thumbAngle; // Update HSV
          updateSelectorColor();
          drawPickerBitmap();
          invalidate();
          return true;
        } else if (distanceFromCenter <= pickerRadius) {
          updateSelectorPosition(x, y);
          updateSelectorColor();
          invalidate();
          return true;
        }
        break;

      case MotionEvent.ACTION_MOVE:
        if (isThumbTouched) {
          thumbAngle = normalizeAngle(angle);
          currentColor = getColorForAngle(thumbAngle);
          hsv[0] = thumbAngle; // Update HSV
          updateSelectorColor();
          drawPickerBitmap();
          invalidate();
          return true;
        } else if (distanceFromCenter <= pickerRadius) {
          updateSelectorPosition(x, y);
          updateSelectorColor();
          invalidate();
          return true;
        }
        break;

      case MotionEvent.ACTION_UP:
        if (isThumbTouched || distanceFromCenter <= pickerRadius) {
          if (listener != null) listener.onColorSelected(currentColor);
          isThumbTouched = false;
          invalidate();
          return true;
        }
        break;
    }
    return super.onTouchEvent(event);
  }

  private float normalizeAngle(float angle) {
    return angle < 0 ? angle + 360 : angle;
  }

  private void updateSelectorPosition(float x, float y) {
    float dx = x - (centerX - pickerRadius);
    float dy = y - (centerY - pickerRadius);

    int bitmapX = (int) dx;
    int bitmapY = (int) dy;

    if (bitmapX >= 0
        && bitmapX < pickerBitmap.getWidth()
        && bitmapY >= 0
        && bitmapY < pickerBitmap.getHeight()) {
      int color = pickerBitmap.getPixel(bitmapX, bitmapY);

      // Ensure the selector only moves within the non-transparent portion of the bitmap
      if (Color.alpha(color) != 0) {
        pickerColor = color;
        currentColor = pickerColor;

        // Update HSV to reflect the new position
        Color.colorToHSV(pickerColor, hsv);

        selectorX = x;
        selectorY = y;

        if (listener != null) listener.onColorSelected(currentColor);
      }
    }
  }

  private void updateSelectorColor() {
    float luminance =
        0.299f * Color.red(currentColor)
            + 0.587f * Color.green(currentColor)
            + 0.114f * Color.blue(currentColor);
    selectorPaint.setColor(luminance < 128 ? Color.WHITE : Color.BLACK);
  }

  public void setColor(int color) {
    // Convert the color to HSV and update the current HSV
    Color.colorToHSV(color, hsv);
    currentColor = color;

    // Update the thumb position and selector position
    updateColorAndCirclePosition();
    updateSelectorColor();
    positionSelectorForCurrentColor();

    // Notify the listener and redraw
    if (listener != null) listener.onColorSelected(currentColor);
    invalidate();
  }

  private void updateColorAndCirclePosition() {
    // Update the thumb's position based on the current hue
    currentColor = Color.HSVToColor(hsv);
    thumbAngle = hsv[0];
  }

  private void positionSelectorForCurrentColor() {
    float cx = centerX - pickerRadius;
    float cy = centerY - pickerRadius;

    // Calculate selector position based on HSV saturation and value
    float saturation = hsv[1];
    float value = hsv[2];

    selectorX = cx + pickerRadius * 2 * saturation;
    selectorY = cy + pickerRadius * 2 * (1 - value);

    // Adjust the selector's position to ensure it stays inside the picker circle
    float dx = selectorX - centerX;
    float dy = selectorY - centerY;
    float distanceFromCenter = (float) Math.sqrt(dx * dx + dy * dy);
    if (distanceFromCenter > pickerRadius) {
      float scale = pickerRadius / distanceFromCenter;
      selectorX = centerX + dx * scale;
      selectorY = centerY + dy * scale;
    }
  }

  public void setOnColorSelectedListener(OnColorSelectedListener listener) {
    this.listener = listener;
  }

  public interface OnColorSelectedListener {
    void onColorSelected(int color);
  }
}

class CustomImageView extends View {
  private Bitmap bitmap; // The background image
  private Paint circlePaint; // The paint for drawing the circle
  private float touchX = -1; // Last touch X position
  private float touchY = -1; // Last touch Y position
  private OnColorChangedListener onColorChangedListener; // Color changed listener

  public CustomImageView(Context context) {
    super(context);

    // Initialize the circle paint
    circlePaint = new Paint();
    circlePaint.setStyle(Paint.Style.STROKE); // Circle style (stroke)
    circlePaint.setAntiAlias(true);
    circlePaint.setStrokeWidth(5);
    circlePaint.setColor(Color.WHITE); // Default circle color
  }

  // Method to set the image from Base64
  public void setImageFromBase64(String base64) {
    byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
    bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

    // Try to locate the color in the bitmap and update the position
    if (bitmap != null) {
      updateCirclePositionToColor(
          Color.RED); // Set the circle position to the first occurrence of Color.RED
    }

    invalidate(); // Refresh the view
  }

  // Method to search for a color in the bitmap and update the circle position
  public void updateCirclePositionToColor(int targetColor) {
    if (bitmap != null) {
      int width = bitmap.getWidth();
      int height = bitmap.getHeight();

      // Scan the bitmap for the target color
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int pixelColor = bitmap.getPixel(x, y);
          if (isColorMatch(pixelColor, targetColor)) {
            touchX = x; // Update the circle position
            touchY = y;
            updateColorAndCirclePosition(
                touchX, touchY); // Update color and position based on found pixel
            invalidate(); // Refresh the view
            return; // Exit once the color is found
          }
        }
      }

      // If the color isn't found, set position to center
      touchX = width / 2f;
      touchY = height / 2f;
      invalidate();
    }
  }

  // Helper method to check if a color matches the target color, considering small variations in the
  // image
  private boolean isColorMatch(int pixelColor, int targetColor) {
    // You can add tolerance here if colors are close but not exactly the same
    return pixelColor == targetColor;
  }

  // Method to update the color and circle position
  private void updateColorAndCirclePosition(float x, float y) {
    if (bitmap != null) {
      int pixelColor = getPixelColor(bitmap, x, y);
      changeColor(pixelColor); // Notify the color change listener
      updateCirclePaintColor(pixelColor); // Update the circle color based on luminance
    }
  }

  // Method to change the paint color
  public void changeColor(int color) {
    if (onColorChangedListener != null) {
      onColorChangedListener.onColorChanged(color);
    }
  }

  // Method to set the OnColorChangedListener
  public void setOnColorChangedListener(OnColorChangedListener listener) {
    this.onColorChangedListener = listener;
  }

  // Method to get the pixel color from the bitmap at a specific position
  private int getPixelColor(Bitmap bitmap, float x, float y) {
    int bitmapWidth = bitmap.getWidth();
    int bitmapHeight = bitmap.getHeight();

    // Scale the touch coordinates based on the view size
    float scaledX = x * bitmapWidth / getWidth();
    float scaledY = y * bitmapHeight / getHeight();

    // Ensure the coordinates are within bounds
    scaledX = Math.max(0, Math.min(scaledX, bitmapWidth - 1));
    scaledY = Math.max(0, Math.min(scaledY, bitmapHeight - 1));

    return bitmap.getPixel((int) scaledX, (int) scaledY);
  }

  // Method to update the circle's paint color based on luminance
  private void updateCirclePaintColor(int pixelColor) {
    float luminance =
        0.299f * Color.red(pixelColor)
            + 0.587f * Color.green(pixelColor)
            + 0.114f * Color.blue(pixelColor);
    circlePaint.setColor(luminance < 128 ? Color.WHITE : Color.BLACK);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // Draw the background image, scaling it to fit the view size
    if (bitmap != null) {
      int viewWidth = getWidth();
      int viewHeight = getHeight();

      // Scale the bitmap to fit the view (keeping the aspect ratio)
      Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, viewWidth, viewHeight, false);
      canvas.drawBitmap(scaledBitmap, 0, 0, null);
    }

    // Draw the circle at the touch position if touch is detected
    if (touchX != -1 && touchY != -1) {
      canvas.drawCircle(touchX, touchY, 20, circlePaint); // Radius = 20
    }
  }

  // Touch listener to update the circle position
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        if (isTouchWithinBitmap(event.getX(), event.getY())) {
          touchX = event.getX();
          touchY = event.getY();
          updateColorAndCirclePosition(touchX, touchY);
          invalidate(); // Refresh the view
        }
        return true;

      case MotionEvent.ACTION_UP:
        // Save the last position
        touchX = event.getX();
        touchY = event.getY();
        return true;
    }
    return false;
  }

  // Helper method to check if the touch position is within the bitmap boundaries
  private boolean isTouchWithinBitmap(float x, float y) {
    if (bitmap == null) {
      return false;
    }
    int bitmapWidth = bitmap.getWidth();
    int bitmapHeight = bitmap.getHeight();

    float scaledX = x * bitmapWidth / getWidth();
    float scaledY = y * bitmapHeight / getHeight();

    return scaledX >= 0 && scaledX < bitmapWidth && scaledY >= 0 && scaledY < bitmapHeight;
  }

  // Interface for color change listener
  public interface OnColorChangedListener {
    void onColorChanged(int newColor);
  }
}
	   /*
       public class ColorPickerView extends View {

            private Paint colorWheelPaint;
            private Paint selectorPaint;
            private Bitmap colorWheelBitmap;
            private int wheelRadius;
            private int centerX, centerY;
            private float selectorX, selectorY;
            private int selectedColor = Color.WHITE;
            private OnColorSelectedListener colorSelectedListener;

            public ColorPickerView(Context context) {
                super(context);
                init();
            }

            private void init() {
                // Paint for drawing the color wheel
                colorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

                // Paint for selection indicator
                selectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                selectorPaint.setStyle(Paint.Style.STROKE);
                selectorPaint.setStrokeWidth(5);
                selectorPaint.setColor(Color.BLACK);
            }

            @Override
            protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);
                // Determine the radius and center of the wheel
                wheelRadius = Math.min(w, h) / 2 - 20;
                centerX = w / 2;
                centerY = h / 2;

                // Generate the color wheel bitmap
                colorWheelBitmap = createColorWheelBitmap(wheelRadius * 2);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                // Draw the color wheel bitmap
                canvas.drawBitmap(colorWheelBitmap, centerX - wheelRadius, centerY - wheelRadius, null);
                // Draw the selection indicator
                canvas.drawCircle(selectorX, selectorY, 20, selectorPaint);
            }

            private Bitmap createColorWheelBitmap(int size) {
                Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                // Create shaders for color gradient
                SweepGradient sweepGradient = new SweepGradient(size / 2, size / 2,
                                                                new int[]{Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED}, null);
                RadialGradient radialGradient = new RadialGradient(size / 2, size / 2, size / 2, Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
                ComposeShader composeShader = new ComposeShader(sweepGradient, radialGradient, PorterDuff.Mode.SRC_OVER);

                colorWheelPaint.setShader(composeShader);

                // Draw the shader on the bitmap
                canvas.drawCircle(size / 2, size / 2, size / 2, colorWheelPaint);
                return bitmap;
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                float dx = event.getX() - centerX;
                float dy = event.getY() - centerY;
                float distanceFromCenter = (float) Math.sqrt(dx * dx + dy * dy);

                // Check if the touch is within the color wheel
                if (distanceFromCenter <= wheelRadius) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:

                            selectorX = event.getX();
                            selectorY = event.getY();
                            selectedColor = getColorAtPoint(dx, dy);

                            if (colorSelectedListener != null) {
                                colorSelectedListener.onColorSelected(selectedColor);

                            }
                            invalidate();

                            return true;
                        case MotionEvent.ACTION_UP:
                            // Trigger the color selection on touch release
                            performClick();
                            return true;
                    }
                }
                return false;
            }

            @Override
            public boolean performClick() {
                super.performClick();
                return true;
            }

            private int getColorAtPoint(float dx, float dy) {
                // Calculate the angle and distance to determine HSV
                double angle = Math.atan2(dy, dx);
                if (angle < 0) angle += 2 * Math.PI;

                float distance = (float) Math.sqrt(dx * dx + dy * dy) / wheelRadius;
                float hue = (float) (angle * 180 / Math.PI); // Convert to degrees
                float saturation = Math.min(distance, 1f);
                float brightness = 1f;

                return Color.HSVToColor(new float[]{hue, saturation, brightness});
            }

            public int getSelectedColor() {
                return selectedColor;
            }

            public void setOnColorSelectedListener(OnColorSelectedListener listener) {
                this.colorSelectedListener = listener;
            }
    }
            public interface OnColorSelectedListener {
                void onColorSelected(int color);

        }*/
