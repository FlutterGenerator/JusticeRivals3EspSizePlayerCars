package JusticeRivals3.ModMenuBY.Ehromtj;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixDrawable extends Drawable {
  private Bitmap canvasBitmap;
  private int fontSize = 15;
  private String matrixText = "MATRIX1234567890";
  private char[] textChar = matrixText.toCharArray();
  private int textLength = textChar.length;
  private Random rand = new Random();
  private int[] textPosition;
  private int columnSize;
  private GradientDrawable gradientDrawable;
  private int animationDelay = 5250; // Initial animation speed (faster)
  private final Handler handler = new Handler();
  private boolean isSlowingDown = true; // Flag for gradual slowdown
  private static final int MAX_DELAY = 5500; // Maximum delay for stopping

  // Runnable to handle the animation updates
  private final Runnable animationRunnable =
      new Runnable() {
        @Override
        public void run() {
          invalidateSelf(); // Schedule the next frame
          if (isSlowingDown) {
            animationDelay += 10; // Gradually increase delay
            if (animationDelay >= MAX_DELAY) {
              isSlowingDown = false; // Stop slowing down when max delay is reached
            }
          }
          handler.postDelayed(this, animationDelay);
        }
      };

  public MatrixDrawable() {
    init();
  }

  private void init() {
    textChar = matrixText.toCharArray();
    textLength = textChar.length;
    gradientDrawable = new GradientDrawable();
  }

  public void setMatrixText(String txt) {
    matrixText = txt;
    textChar = matrixText.toCharArray();
    textLength = textChar.length;
  }

  public void setTextSize(int size) {
    fontSize = size;
  }

  public void setBackgroundDrawable(GradientDrawable drawable) {
    this.gradientDrawable = drawable;
  }

  private void drawText(Canvas canvas) {
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setTextSize(fontSize);

    for (int i = 0; i < textPosition.length; i++) {
      // Randomize text color for each column
      paint.setColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));

      // Draw a random character from the matrixText string
      canvas.drawText(
          "" + textChar[rand.nextInt(textLength)], i * fontSize, textPosition[i] * fontSize, paint);

      // Reset position when character goes off the screen
      if (textPosition[i] * fontSize > canvas.getHeight() && Math.random() > 0.975) {
        textPosition[i] = 0;
      }

      // Increment the position
      textPosition[i]++;
    }
  }

  @Override
  public void draw(Canvas canvas) {
    // Draw the background gradient
    gradientDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    gradientDrawable.draw(canvas);

    // Draw the matrix text
    drawText(canvas);

    // Schedule the next frame
    if (isSlowingDown || animationDelay < MAX_DELAY) {
      handler.postDelayed(animationRunnable, animationDelay);
    }
  }

  @Override
  protected void onBoundsChange(Rect bounds) {
    super.onBoundsChange(bounds);
    canvasBitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
    columnSize = bounds.width() / fontSize;
    textPosition = new int[columnSize + 1];
    for (int x = 0; x < columnSize; x++) {
      textPosition[x] = 1;
    }
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }

  @Override
  public void setAlpha(int alpha) {
    // Not needed for this implementation
  }

  @Override
  public void setColorFilter(ColorFilter colorFilter) {
    // Not needed for this implementation
  }
}

class ParticleMeshDrawable extends Drawable {

  private final List<Particle> particles = new ArrayList<>();
  private final Random random = new Random();
  private final int particleCount = 100; // Number of particles
  private final int lineDistance = 150; // Maximum distance for connecting particles
  private final Paint particlePaint = new Paint();
  private final Paint linePaint = new Paint();
  private GradientDrawable gradientDrawable;

  public ParticleMeshDrawable() {
    // Set up paint for particles and lines
    particlePaint.setStyle(Paint.Style.FILL);
    linePaint.setStyle(Paint.Style.STROKE);
    linePaint.setColor(Color.WHITE);
    linePaint.setStrokeWidth(2);

    // Create particles at random positions
    for (int i = 0; i < particleCount; i++) {
      particles.add(new Particle(random.nextInt(1080), random.nextInt(1920)));
    }

    // Start particle animation in a separate thread
    startAnimation();
  }

  @Override
  public void draw(Canvas canvas) {
    if (canvas != null) {
      // If a gradient is set, draw it first
      if (gradientDrawable != null) {
        gradientDrawable.setBounds(getBounds());
        gradientDrawable.draw(canvas); // Draw the gradient background
      }

      // Draw particles and connect them with lines if they are close
      for (Particle p1 : particles) {
        // Draw particle
        particlePaint.setColor(p1.color);
        canvas.drawCircle(p1.x, p1.y, p1.size, particlePaint);

        // Draw lines between nearby particles
        for (Particle p2 : particles) {
          float distance = p1.distanceTo(p2);
          if (distance < lineDistance) {
            int alpha = (int) ((1 - (distance / lineDistance)) * 255); // Fade based on distance
            linePaint.setColor(Color.argb(alpha, 255, 255, 255));
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, linePaint);
          }
        }
      }
    }
  }

  @Override
  public void setAlpha(int alpha) {
    // Implement alpha control if needed
  }

  @Override
  public void setColorFilter(android.graphics.ColorFilter colorFilter) {
    // Handle color filters if necessary
  }

  @Override
  public int getOpacity() {
    return android.graphics.PixelFormat.TRANSLUCENT;
  }

  private void startAnimation() {
    final android.os.Handler handler = new android.os.Handler();
    final Runnable runnable =
        new Runnable() {
          @Override
          public void run() {
            // Update particle positions and redraw
            for (Particle particle : particles) {
              particle.updatePosition(getBounds().width(), getBounds().height());
            }
            invalidateSelf(); // Trigger a redraw
            handler.postDelayed(this, 16); // Approximately 60 FPS
          }
        };
    handler.post(runnable); // Start the animation
  }

  // Method to set the gradient background dynamically
  public void setBackgroundDrawable(GradientDrawable drawable) {
    this.gradientDrawable = drawable;
  }

  private static class Particle {
    float x, y, vx, vy;
    int size;
    int color;

    Particle(float x, float y) {
      Random random = new Random();
      this.x = x;
      this.y = y;
      this.vx = random.nextFloat() * 4 - 2; // Random velocity (-2 to 2)
      this.vy = random.nextFloat() * 4 - 2;
      this.size = random.nextInt(8) + 5; // Random size (5 to 12)
      this.color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    void updatePosition(int width, int height) {
      x += vx;
      y += vy;

      // Bounce off edges
      if (x < 0 || x > width) vx = -vx;
      if (y < 0 || y > height) vy = -vy;
    }

    float distanceTo(Particle other) {
      return (float) Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
  }
}

class RotatingNeonFrameDrawable extends GradientDrawable {
  private Paint paint; // Paint for the neon stroke
  private Paint backgroundPaint; // Paint for the background
  private int[] colors; // Gradient colors
  private float[] positions; // Gradient stops
  private int strokeWidth; // Stroke width
  private float cornerRadius; // Radius for rounded corners
  private long startTime; // Animation start time
  private int backgroundColor; // Background color
  private float timeMultiplier; // Controls animation speed
  private GradientDirection gradientDirection;

  // Enum to define gradient direction
  public enum GradientDirection {
    LEFT_TO_RIGHT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP,
    RIGHT_TO_LEFT
  }

  public RotatingNeonFrameDrawable(
      int[] colors,
      float[] positions,
      int strokeWidth,
      float cornerRadius,
      GradientDirection gradientDirection,
      int backgroundColor,
      float timeMultiplier) {
    this.colors =
        colors != null
            ? colors
            : new int[] {0xFFFF007F, 0xFF7F00FF, 0xFF007FFF}; // Default gradient colors
    this.positions =
        positions != null ? positions : new float[] {0.0f, 0.5f, 1.0f}; // Default gradient stops
    this.strokeWidth = strokeWidth;
    this.cornerRadius = cornerRadius;
    this.gradientDirection =
        gradientDirection != null ? gradientDirection : GradientDirection.LEFT_TO_RIGHT;
    this.backgroundColor = backgroundColor;
    this.timeMultiplier = timeMultiplier;

    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(strokeWidth);

    backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    backgroundPaint.setColor(backgroundColor);

    startTime = System.currentTimeMillis();
  }

  @Override
  public void draw(Canvas canvas) {
    Rect bounds = getBounds();
    float elapsed =
        (System.currentTimeMillis() - startTime)
            / (10000f / timeMultiplier); // Adjust speed using timeMultiplier

    // Calculate the gradient offset for animation
    float offset = (elapsed % 2) / 2.0f;

    // Create the gradient based on the specified direction
    LinearGradient gradient;
    switch (gradientDirection) {
      case LEFT_TO_RIGHT:
        gradient =
            new LinearGradient(
                bounds.left,
                bounds.top,
                bounds.right,
                bounds.top,
                colors,
                calculatePositions(offset),
                Shader.TileMode.MIRROR);
        break;
      case TOP_TO_BOTTOM:
        gradient =
            new LinearGradient(
                bounds.left,
                bounds.top,
                bounds.left,
                bounds.bottom,
                colors,
                calculatePositions(offset),
                Shader.TileMode.MIRROR);
        break;
      case BOTTOM_TO_TOP:
        gradient =
            new LinearGradient(
                bounds.left,
                bounds.bottom,
                bounds.left,
                bounds.top,
                colors,
                calculatePositions(offset),
                Shader.TileMode.MIRROR);
        break;
      case RIGHT_TO_LEFT:
        gradient =
            new LinearGradient(
                bounds.right,
                bounds.top,
                bounds.left,
                bounds.top,
                colors,
                calculatePositions(offset),
                Shader.TileMode.MIRROR);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + gradientDirection);
    }

    paint.setShader(gradient);

    // Create a rect for the background and stroke
    RectF rectF =
        new RectF(
            bounds.left + strokeWidth / 2f,
            bounds.top + strokeWidth / 2f,
            bounds.right - strokeWidth / 2f,
            bounds.bottom - strokeWidth / 2f);

    // Draw the background color with rounded corners
    canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, backgroundPaint);

    // Draw the neon frame with rounded corners
    canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

    // Redraw for animation
    invalidateSelf();
  }

  private float[] calculatePositions(float offset) {
    float[] adjustedPositions = new float[positions.length];
    for (int i = 0; i < positions.length; i++) {
      adjustedPositions[i] = (positions[i] + offset) % 1.0f;
    }
    return adjustedPositions;
  }

  @Override
  public void setAlpha(int alpha) {
    paint.setAlpha(alpha);
    backgroundPaint.setAlpha(alpha);
  }

  @Override
  public void setColorFilter(android.graphics.ColorFilter colorFilter) {
    paint.setColorFilter(colorFilter);
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }

  // Dynamic setters for customization
  public void setStrokeWidth(int strokeWidth) {
    this.strokeWidth = strokeWidth;
    paint.setStrokeWidth(strokeWidth);
    invalidateSelf();
  }

  public void setCornerRadius(float cornerRadius) {
    this.cornerRadius = cornerRadius;
    invalidateSelf();
  }

  public void setGradientDirection(GradientDirection direction) {
    this.gradientDirection = direction;
    invalidateSelf();
  }

  public void setBackgroundColor(int backgroundColor) {
    this.backgroundColor = backgroundColor;
    backgroundPaint.setColor(backgroundColor);
    invalidateSelf();
  }

  public void setTimeMultiplier(float timeMultiplier) {
    this.timeMultiplier = timeMultiplier;
    invalidateSelf();
  }
}

class GradientTextView extends TextView {
  private DisplayMetrics sDisplayMetrics;
  private float colorSpace;
  private float colorSpeed;
  private int[] colors;
  private boolean isDynamic;
  private LinearGradient mLinearGradient;
  private Matrix mMatrix;
  private float mTranslate;

  public GradientTextView(Context context) {
    this(context, null);
  }

  public GradientTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.colors = new int[] {0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF}; // More visible colors
    this.isDynamic = true;
    setLayerType(LAYER_TYPE_HARDWARE, null);
    this.colorSpace = dp2px(300); // Increased gradient length
    this.colorSpeed = dp2px(2); // Faster speed
    this.mMatrix = new Matrix();
    initPaint();
  }

  private void initPaint() {
    if (this.colors != null && this.colors.length > 0) {
      mLinearGradient =
          new LinearGradient(0, 0, colorSpace, 0, colors, null, Shader.TileMode.MIRROR);
      getPaint().setShader(mLinearGradient);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    // Update gradient to match view width
    colorSpace = w > 0 ? w : dp2px(300);
    initPaint();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    mTranslate += colorSpeed;

    if (mTranslate > colorSpace * 2) { // Reset translation to prevent overflow
      mTranslate -= colorSpace * 2;
    }

    mMatrix.setTranslate(mTranslate, 0);

    if (mLinearGradient != null) {
      mLinearGradient.setLocalMatrix(mMatrix);
    }

    super.onDraw(canvas);

    if (isDynamic) {
      postInvalidateOnAnimation();
    }
  }

  // Helper methods
  private int dp2px(float dp) {
    return Math.round(dp * getDisplayMetrics().density);
  }

  private DisplayMetrics getDisplayMetrics() {
    if (sDisplayMetrics == null) {
      sDisplayMetrics = Resources.getSystem().getDisplayMetrics();
    }
    return sDisplayMetrics;
  }

  // Public interface
  public void setColors(int... colors) {
    if (colors != null && colors.length > 0) {
      this.colors = colors;
      initPaint();
    }
  }

  public void setDynamicEnabled(boolean enabled) {
    this.isDynamic = enabled;
    postInvalidateOnAnimation();
  }

  public boolean getDynamicEnabled() {
    return this.isDynamic;
  }
}
