package JusticeRivals3.ModMenuBY.Ehromtj;
// *******  A **********///
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;
import static android.widget.RelativeLayout.ALIGN_PARENT_END;
import static android.widget.RelativeLayout.ALIGN_PARENT_START;
import static android.widget.RelativeLayout.CENTER_HORIZONTAL;
import static android.widget.RelativeLayout.CENTER_VERTICAL;

public class SimpleVerticalSeekBar extends SeekBar {

  public SimpleVerticalSeekBar(Context context) {
    super(context);
  }

  public SimpleVerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public SimpleVerticalSeekBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(h, w, oldh, oldw);
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(heightMeasureSpec, widthMeasureSpec);
    setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
  }

  protected void onDraw(Canvas c) {
    c.rotate(-90);
    c.translate(-getHeight(), 0);

    super.onDraw(c);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (!isEnabled()) {
      return false;
    }

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
      case MotionEvent.ACTION_UP:
        setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        break;

      case MotionEvent.ACTION_CANCEL:
        break;
    }
    return true;
  }
}

class CustomSeekBar extends SeekBar {
  private Paint textPaint;
  private String thumbText;
  private GradientDrawable thumbDrawable;
  private int thumbWidth = 100; // Default thumb width
  private int thumbHeight = 60; // Default thumb height
  private int strokeWidth = 5; // Default stroke width
  private int strokeColor = Color.BLACK; // Default stroke color
  private float cornerRadius = 20f; // Default corner radius
  private int shape = 0; // 0 for rectangle, 1 for circle

  public CustomSeekBar(Context context) {
    super(context);
    init();
  }

  private void init() {
    // Initialize text paint
    textPaint = new Paint();
    textPaint.setColor(Color.WHITE); // Text color
    textPaint.setTextSize(40); // Text size
    textPaint.setTextAlign(Paint.Align.CENTER);
    // Create a gradient drawable for the thumb (for rectangle only)
    thumbDrawable = new GradientDrawable();
    thumbDrawable.setColors(new int[] {Color.parseColor("#FF5722"), Color.parseColor("#FFC107")});
    thumbDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    updateThumbShape(); // Set initial shape and corner radius
  }

  @Override
  protected synchronized void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // Get current progress
    int progress = getProgress();
    thumbText = String.valueOf(progress);
    // Calculate thumb position
    int thumbX =
        getThumbOffset()
            + (int)
                ((getWidth() - getPaddingLeft() - getPaddingRight())
                    * (progress / (float) getMax()));
    if (shape == 0) { // Rectangle
      // Set bounds for the drawable (thumb size)
      thumbDrawable.setBounds(
          thumbX - thumbWidth / 2,
          getHeight() / 2 - thumbHeight / 2,
          thumbX + thumbWidth / 2,
          getHeight() / 2 + thumbHeight / 2);
      // Draw the gradient drawable as the thumb
      thumbDrawable.draw(canvas);
      // Draw the stroke around the rectangle if stroke width is greater than zero
      if (strokeWidth > 0) {
        Paint strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
        canvas.drawRoundRect(
            thumbX - thumbWidth / 2,
            getHeight() / 2 - thumbHeight / 2,
            thumbX + thumbWidth / 2,
            getHeight() / 2 + thumbHeight / 2,
            cornerRadius,
            cornerRadius,
            strokePaint);
      }
    } else if (shape == 1) { // Circle
      int diameter = Math.min(thumbWidth, thumbHeight);
      int radius = diameter / 2;
      // Create and draw RadialGradient for circle shape
      RadialGradient radialGradient =
          new RadialGradient(
              thumbX,
              getHeight() / 2,
              radius,
              new int[] {thumbDrawable.getColors()[0], thumbDrawable.getColors()[1]},
              null,
              Shader.TileMode.CLAMP);
      Paint circlePaint = new Paint();
      circlePaint.setShader(radialGradient);
      canvas.drawCircle(thumbX, getHeight() / 2, radius, circlePaint);
      // Draw the stroke around the circle if stroke width is greater than zero
      if (strokeWidth > 0) {
        Paint strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(thumbX, getHeight() / 2, radius + strokeWidth / 2, strokePaint);
      }
    }
    // Draw the text centered in the shape with padding
    float textY =
        getHeight() / 2
            + (textPaint.getTextSize() / 4); // Center Y position considering text height
    canvas.drawText(thumbText, thumbX, textY, textPaint);
  }

  public void setThumbSize(int width, int height) {
    this.thumbWidth = width;
    this.thumbHeight = height;
    invalidate(); // Redraw the SeekBar
  }

  public void setThumbColors(int startColor, int endColor) {
    // Update both rectangle and circle colors
    thumbDrawable.setColors(new int[] {startColor, endColor});
    invalidate(); // Redraw the SeekBar
  }

  public void setStrokeWidth(int width) {
    this.strokeWidth = width;
    invalidate(); // Redraw the SeekBar
  }

  public void setStrokeColor(int color) {
    this.strokeColor = color;
    invalidate(); // Redraw the SeekBar
  }

  public void setCornerRadius(float radius) {
    this.cornerRadius = radius;
    updateThumbShape();
    invalidate();
  }

  public void setShape(int shape) {
    this.shape = shape;
    invalidate();
  }

  private void updateThumbShape() {
    if (shape == 0) { // Rectangle
      thumbDrawable.setCornerRadius(cornerRadius);
      strokeWidth = Math.max(strokeWidth, 0);
    } else if (shape == 1) { // Circle
      strokeWidth = Math.max(strokeWidth, 0);
      // No need to update drawable since we are drawing it directly in onDraw.
    }
    invalidate();
  }

  public void setTextSize(float size) {
    textPaint.setTextSize(size);
    invalidate();
  }

  public void setTextColor(int color) {
    textPaint.setColor(color);
    invalidate();
  }
}

class VerticalColorSeekBar extends View {
  private Paint paint, strokepaint;
  private Paint thumbPaint;
  private int width;
  private int height;
  private float thumbY = 0; // Thumb's vertical position
  private float[] hsv = {0, 1, 1}; // Initial HSV (Hue, Saturation, Value)
  private OnColorChangeListener colorChangeListener;

  public VerticalColorSeekBar(Context context) {
    super(context);
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(25);
    paint.setColor(Color.WHITE);

    strokepaint = new Paint();
    strokepaint.setStyle(Paint.Style.STROKE);
    strokepaint.setStrokeWidth(5);
    strokepaint.setColor(Color.WHITE);

    thumbPaint = new Paint();
    thumbPaint.setStyle(Paint.Style.STROKE);
    thumbPaint.setStrokeWidth(5);
    thumbPaint.setColor(Color.WHITE); // Initial thumb color
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    width = w;
    height = h;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // Draw the HSV gradient
    for (int i = 0; i < height; i++) {
      float hue = (360f * i) / height; // Map position to Hue (0-360)
      hsv[0] = hue;
      paint.setColor(Color.HSVToColor(hsv));
      canvas.drawLine(width / 2f - 25, i, width / 2f + 25, i, paint); // Draw track stroke
      canvas.drawRect(0, 0, width, height, strokepaint);
      // canvas.drawRect(0, i, width, i + 1, paint);
    }
    // Draw the thumb
    // thumbPaint.setColor(Color.HSVToColor(hsv));
    // canvas.drawRect(0, thumbY - 10, width, thumbY + 10, thumbPaint); // Thumb rectangle
    canvas.drawRect(width / 8f, thumbY - 10, 3 * width / 3.5f, thumbY + 10, thumbPaint);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        thumbY = Math.max(0, Math.min(event.getY(), height)); // Constrain thumb position
        float hue = (360f * thumbY) / height; // Map thumb position to Hue
        hsv[0] = hue;
        // thumbPaint.setColor(Color.HSVToColor(hsv)); // Update thumb color
        if (colorChangeListener != null) {
          colorChangeListener.onColorChanged(Color.HSVToColor(hsv));
        }
        invalidate();
        return true;
    }
    return super.onTouchEvent(event);
  }

  public void setOnColorChangeListener(OnColorChangeListener listener) {
    this.colorChangeListener = listener;
  }

  public interface OnColorChangeListener {
    void onColorChanged(int color);
  }
}

class StarView extends Button {
  private Paint fillPaint;
  private Paint strokePaint;
  private Path starPath;
  private int size = 100;
  private float rotationAngle = 0; // Rotation angle in degrees
  private float cornerRadius = 0; // Corner radius for the star

  public StarView(Context context) {
    super(context);
    init();
  }

  private void init() {
    fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    fillPaint.setStyle(Paint.Style.FILL);
    strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    strokePaint.setStyle(Paint.Style.STROKE);
    starPath = new Path();
  }

  public void setStarProperties(
      int fillColor, int strokeColor, float strokeWidth, int size, float cornerRadius) {
    fillPaint.setColor(fillColor);
    strokePaint.setColor(strokeColor);
    strokePaint.setStrokeWidth(strokeWidth);
    this.size = size;
    this.cornerRadius = cornerRadius;
    invalidate();
  }

  public void setRotationAngle(float angle) {
    this.rotationAngle = angle;
    invalidate();
  }

  private void createStarPath() {
    starPath.reset();
    float midX = getWidth() / 2f;
    float midY = getHeight() / 2f;
    float outerRadius = size / 2f;
    float innerRadius = outerRadius / 2.5f;

    for (int i = 0; i < 10; i++) {
      float angle = (float) (Math.PI * i / 5);
      float r = (i % 2 == 0) ? outerRadius : innerRadius;
      float x = (float) (midX + r * Math.cos(angle));
      float y = (float) (midY - r * Math.sin(angle));
      if (i == 0) {
        starPath.moveTo(x, y);
      } else {
        starPath.lineTo(x, y);
      }
    }
    starPath.close();

    // Apply corner radius using a PathEffect
    PathEffect cornerEffect = new CornerPathEffect(cornerRadius);
    fillPaint.setPathEffect(cornerEffect);
    strokePaint.setPathEffect(cornerEffect);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // Save the canvas state
    int saveCount = canvas.save();
    // Rotate the canvas around the center of the view
    canvas.rotate(rotationAngle, getWidth() / 2f, getHeight() / 2f);
    // Create and draw the star path
    createStarPath();
    canvas.drawPath(starPath, fillPaint);
    canvas.drawPath(starPath, strokePaint);
    // Restore the canvas to its previous state
    canvas.restore();
    // Draw the button text
    super.onDraw(canvas);
  }
}

class CustomBorderTitleView extends LinearLayout {
  private Paint textPaint;
  private Paint fillPaint;
  private GradientDrawable borderDrawable;
  private String labelText = " Full Name "; // Title text with leading/trailing space
  private float labelTextSize = 35f; // Title text size
  private float labelTextY = 25f; // Y position of the title
  private int labelPadding = 80; // Padding between text and border
  private int strokeWidth = 5; // Stroke width for the border
  private int borderRadius = 15; // Corner radius of the border
  private int borderColor = Color.parseColor("#004d40"); // Border color
  private int backgroundTextcolor = (Color.WHITE);
  private int textPaddingLeft = 5; // Padding on the left side of the text
  private int textPaddingRight = 5; // Padding on the right side of the text
  private int textPaddingTop = 5; // Padding on the top side of the text
  private int textPaddingBottom = 5; // Padding on the bottom side of the text
  private int marginLeft = 5; // Margin for the left side of the border
  private int marginTop = 5; // Margin for the top side of the border
  private int marginRight = 5; // Margin for the right side of the border
  private int marginBottom = 5; // Margin for the bottom side of the border

  public CustomBorderTitleView(Context context) {
    super(context);
    init(context);
  }

  private void init(Context context) {
    // Set the layout orientation
    setOrientation(VERTICAL);
    // Initialize the text paint for the title
    textPaint = new Paint();
    textPaint.setColor(Color.RED); // Default text color
    textPaint.setTextSize(labelTextSize);
    textPaint.setAntiAlias(true);
    textPaint.setStyle(Paint.Style.FILL);
    // Initialize the fill paint for erasing stroke behind text
    fillPaint = new Paint();
    fillPaint.setStyle(Paint.Style.FILL);
    fillPaint.setColor(backgroundTextcolor); // Background color to erase stroke
    // Initialize the border drawable
    borderDrawable = new GradientDrawable();
    borderDrawable.setStroke(strokeWidth, borderColor);
    borderDrawable.setCornerRadius(borderRadius);
    setBackground(borderDrawable); // Set the border as the background
    // Add padding to the view
    setPadding(labelPadding, (int) labelTextSize + labelPadding, labelPadding, labelPadding);
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    // Apply margins to the gradient drawable (border)
    RectF borderRect =
        new RectF(marginLeft, marginTop, getWidth() - marginRight, getHeight() - marginBottom);
    borderDrawable.setBounds(
        (int) borderRect.left,
        (int) borderRect.top,
        (int) borderRect.right,
        (int) borderRect.bottom);
    borderDrawable.draw(canvas); // Draw the border with margins
    // Calculate the position of the label text with added padding (for space around the text)
    float x = labelPadding + strokeWidth + textPaddingLeft; // Left padding + text padding
    float y = labelTextY + textPaddingTop; // Y position of the text + top padding

    // Erase the border stroke effect behind the text
    RectF rect =
        new RectF(
            x,
            y - labelTextSize - textPaddingTop,
            x + textPaint.measureText(labelText) + textPaddingLeft + textPaddingRight,
            y + labelTextSize + textPaddingBottom);
    canvas.drawRoundRect(rect, borderRadius, borderRadius, fillPaint);

    // Draw the label text on top
    canvas.drawText(labelText, x, y, textPaint);
  }

  public void setLabelText(String text) {
    this.labelText = text;
    invalidate(); // Redraw the view
  }

  public void setLabelTextSize(float size) {
    this.labelTextSize = size;
    textPaint.setTextSize(size);
    invalidate();
  }

  public void setLabelTextColor(int color) {
    this.textPaint.setColor(color);
    invalidate();
  }

  public void setLabelPadding(int padding) {
    this.labelPadding = padding;
    setPadding(padding, padding, padding, padding);
    invalidate();
  }

  public void setLabelTextY(float yPosition) {
    this.labelTextY = yPosition;
    invalidate();
  }

  public void setBorderColor(int color) {
    this.borderColor = color;
    borderDrawable.setStroke(strokeWidth, color);
    invalidate();
  }

  public void setBackgroundTextColor(int color) {
    this.backgroundTextcolor = color;
    fillPaint.setColor(color); // Background color to erase stroke

    invalidate();
  }

  public void setStrokeWidth(int width) {
    this.strokeWidth = width;
    borderDrawable.setStroke(width, borderColor);
    invalidate();
  }

  public void setBorderRadius(int radius) {
    this.borderRadius = radius;
    borderDrawable.setCornerRadius(radius);
    invalidate();
  }
  // Set padding for the text inside the border (left, top, right, bottom)
  public void setTextPadding(int left, int top, int right, int bottom) {
    this.textPaddingLeft = left;
    this.textPaddingTop = top;
    this.textPaddingRight = right;
    this.textPaddingBottom = bottom;
    invalidate(); // Redraw to apply padding change
  }
  // Set margins for the border (outside the rectangle)
  public void setBorderMargin(int left, int top, int right, int bottom) {
    this.marginLeft = left;
    this.marginTop = top;
    this.marginRight = right;
    this.marginBottom = bottom;
    invalidate(); // Redraw to apply margin change
  }
}
