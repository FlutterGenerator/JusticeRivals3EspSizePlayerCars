// Please don't replace listeners with lambda!

package JusticeRivals3.ModMenuBY.Ehromtj;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import static android.widget.RelativeLayout.CENTER_HORIZONTAL;
import static android.widget.RelativeLayout.CENTER_VERTICAL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import android.graphics.drawable.GradientDrawable.Orientation;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import android.graphics.Color;
import android.graphics.Canvas;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.animation.ObjectAnimator;
import android.graphics.PorterDuffColorFilter;
import android.widget.TableRow.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.icu.text.NumberFormat;
import android.graphics.drawable.RippleDrawable;

public class MenuV5 extends Service {
  // ********** Here you can easly change the menu appearance **********//
  public static final String TAG = "Mod_Menu"; // Tag for logcat
  int TEXT_COLOR = Color.DKGRAY;
  int TEXT_COLOR_2 = Color.DKGRAY;
  int TextColor = Color.parseColor("#FFFFFF");
  int TextColor2 = Color.parseColor("#000000");
  int BTN_COLOR = Color.BLACK;
  int MENU_BG_COLOR = Color.BLACK; // #AARRGGBB
  int MENU_FEATURE_BG_COLOR = Color.BLACK; // #AARRGGBB
  int MENU_WIDTH = 300;
  int MENU_HEIGHT = 220;
  int RBG = Color.BLACK;
  int currentColor;
  float MENU_CORNER = 25.0f;
  int ICON_SIZE = 50; // Change both width and height of image
  float ICON_ALPHA = 0.7f; // Transparent
  int ToggleON = Color.GREEN;
  int ToggleOFF = Color.RED;
  int BtnON = Color.parseColor("#1b5e20");
  int BtnOFF = Color.parseColor("#7f0000");
  int CategoryBG = Color.parseColor("#2F3D4C");
  int SeekBarColor = Color.parseColor("#80CBC4");
  int SeekBarProgressColor = Color.parseColor("#FFFFFF");
  int CheckBoxColor = Color.parseColor("#80CBC4");
  int RadioColor = Color.parseColor("#FFFFFF");
  String NumberTxtColor = "#41c300";
  // ********************************************************************//

  RelativeLayout mCollapsed, mRootContainer;
  LinearLayout mExpanded, patches, mSettings, mCollapse;
  LinearLayout.LayoutParams scrlLLExpanded, scrlLL;
  WindowManager mWindowManager;
  WindowManager.LayoutParams params;
  ImageView startimage;
  FrameLayout rootFrame;
  ScrollView scrollView;

  // ********** ESP DRAW ***********//

  public static native void DrawOn(ESPView espView, Canvas canvas);

  private WindowManager.LayoutParams espParams;
  private ESPView overlayView;

  // **************//

  boolean stopChecking;

  // initialize methods from the native library
  native void setTitleText(TextView textView);

  native void setHeadingText(TextView textView);

  native String Icon();

  native String Background3();

  LinearLayout linh,
      line1,
      line2,
      line3,
      line4,
      line5,
      tablayout,
      tablayoutButton,
      linear13,
      ToastLayout,
      LineTop,
      linearLayoutthem,
      LineTap;
  GradientDrawable gdtabChecked,
      textdesign,
      Closedesign,
      CategoryBGC,
      gdtabUnchecked,
      CRNHVM,
      design,
      designCheck,
      Backgroundscorll,
      Backgroundscorllpage,
      Tabcheck,
      TabUncheck,
      zs,
      Z;

  native String IconWebViewData();

  native String[] getFeatureList();

  native String[] settingsList();

  native boolean isGameLibLoaded();
  // endregion

  private int getLayoutType() {
    if (Build.VERSION.SDK_INT >= 26) {
      return 2038;
    }
    if (Build.VERSION.SDK_INT >= 24) {
      return 2002;
    }
    if (Build.VERSION.SDK_INT >= 23) {
      return 2005;
    }
    return 2003;
  }

  // When this Class is called the code in this function will be executed
  @Override
  public void onCreate() {
    super.onCreate();
    Preferences.context = this;
    this.overlayView = new ESPView((Context) this);

    // Create the menu
    initFloating();
    DrawCanvas();

    // Create a handler for this Class
    final Handler handler = new Handler();
    handler.post(
        new Runnable() {
          public void run() {
            Thread();
            handler.postDelayed(this, 1000);
          }
        });
  }

  private void DrawCanvas() {

    WindowManager.LayoutParams layoutParams;
    this.espParams =
        layoutParams =
            new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                this.getLayoutType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
    layoutParams.gravity = Gravity.TOP | Gravity.START;
    this.espParams.x = 0;
    this.espParams.y = 100;
    this.mWindowManager.addView((View) this.overlayView, (ViewGroup.LayoutParams) this.espParams);
  }

  // Here we write the code for our Menu
  // Reference:
  // https://www.androidhive.info/2016/11/android-floating-widget-like-facebook-chat-head/
  private void initFloating() {
    rootFrame = new FrameLayout(this); // Global markup
    rootFrame.setOnTouchListener(onTouchListener());
    mRootContainer =
        new RelativeLayout(
            this); // Markup on which two markups of the icon and the menu itself will be placed
    mCollapsed = new RelativeLayout(this); // Markup of the icon (when the menu is minimized)
    mCollapsed.setVisibility(View.VISIBLE);
    mCollapsed.setAlpha(ICON_ALPHA);

    // ********** The box of the mod menu **********
    mExpanded = new LinearLayout(this); // Menu markup (when the menu is expanded)
    mExpanded.setVisibility(View.GONE);
    mExpanded.setBackgroundColor(MENU_BG_COLOR);
    mExpanded.setOrientation(LinearLayout.VERTICAL);
    mExpanded.setPadding(1, 1, 1, 1); // So borders would be visible
    mExpanded.setLayoutParams(new LinearLayout.LayoutParams(dp(MENU_WIDTH), WRAP_CONTENT));
    GradientDrawable gdMenuBody = new GradientDrawable();
    gdMenuBody.setCornerRadius(MENU_CORNER); // Set corner
    gdMenuBody.setColor(MENU_BG_COLOR); // Set background color
    gdMenuBody.setStroke(4, Color.DKGRAY); // Set border
    mExpanded.setBackground(gdMenuBody); // Apply GradientDrawable to it

    // ********** The icon to open mod menu **********
    startimage = new ImageView(this);
    startimage.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
    int applyDimension =
        (int)
            TypedValue.applyDimension(
                1, ICON_SIZE, getResources().getDisplayMetrics()); // Icon size
    startimage.getLayoutParams().height = applyDimension;
    startimage.getLayoutParams().width = applyDimension;
    // startimage.requestLayout();
    startimage.setScaleType(ImageView.ScaleType.FIT_XY);
    byte[] decode = Base64.decode(Icon(), 0);
    startimage.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
    ((ViewGroup.MarginLayoutParams) startimage.getLayoutParams()).topMargin =
        convertDipToPixels(10);
    // Initialize event handlers for buttons, etc.
    startimage.setOnTouchListener(onTouchListener());
    startimage.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View view) {
            mCollapsed.setVisibility(View.GONE);
            mExpanded.setVisibility(View.VISIBLE);
          }
        });

    // ********** The icon in Webview to open mod menu **********
    WebView wView = new WebView(this); // Icon size width=\"50\" height=\"50\"
    wView.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
    int applyDimension2 =
        (int)
            TypedValue.applyDimension(
                1, ICON_SIZE, getResources().getDisplayMetrics()); // Icon size
    wView.getLayoutParams().height = applyDimension2;
    wView.getLayoutParams().width = applyDimension2;
    wView.loadData(
        "<html>"
            + "<head></head>"
            + "<body style=\"margin: 0; padding: 0\">"
            + "<img src=\""
            + IconWebViewData()
            + "\" width=\""
            + ICON_SIZE
            + "\" height=\""
            + ICON_SIZE
            + "\" >"
            + "</body>"
            + "</html>",
        "text/html",
        "utf-8");
    wView.setBackgroundColor(0x00000000); // Transparent
    wView.setAlpha(ICON_ALPHA);
    wView.setOnTouchListener(onTouchListener());

    // ********** Settings icon **********
    TextView settings =
        new TextView(this); // Android 5 can't show ⚙, instead show other icon instead
    settings.setText(
        Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ? "" : "\uD83D\uDD27");
    settings.setTextColor(TEXT_COLOR);
    settings.setTypeface(Typeface.DEFAULT_BOLD);
    settings.setTextSize(20.0f);
    RelativeLayout.LayoutParams rlsettings =
        new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    rlsettings.addRule(ALIGN_PARENT_RIGHT);
    settings.setLayoutParams(rlsettings);
    settings.setOnClickListener(
        new View.OnClickListener() {
          boolean settingsOpen;

          @Override
          public void onClick(View v) {
            try {
              settingsOpen = !settingsOpen;
              if (settingsOpen) {
                scrollView.removeView(patches);
                scrollView.addView(mSettings);
                scrollView.scrollTo(0, 0);
              } else {
                scrollView.removeView(mSettings);
                scrollView.addView(patches);
              }
            } catch (IllegalStateException e) {
            }
          }
        });

    // ********** Settings **********
    mSettings = new LinearLayout(this);
    mSettings.setOrientation(LinearLayout.VERTICAL);
    featureList(settingsList(), mSettings);

    // ********** Title text **********
    RelativeLayout titleText = new RelativeLayout(this);
    titleText.setPadding(10, 5, 10, 5);
    titleText.setVerticalGravity(16);

    TextView title = new TextView(this);
    title.setTextColor(TEXT_COLOR);
    title.setTextSize(18.0f);
    title.setGravity(Gravity.CENTER);
    RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
    title.setLayoutParams(rl);
    setTitleText(title);
    ObjectAnimator colorAnimator =
        ObjectAnimator.ofArgb(
            title, "textColor", Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA);
    colorAnimator.setDuration(2000); // Duraci贸n de la animaci贸n en milisegundos
    colorAnimator.setRepeatCount(
        ObjectAnimator.INFINITE); // Repetir infinitamente (puedes ajustar seg煤n tus necesidades)
    colorAnimator.setRepeatMode(ObjectAnimator.REVERSE); // Invertir la animaci贸n al repetir
    colorAnimator.start();
    title.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    // ********** Heading text **********
    TextView heading = new TextView(this);
    heading.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    heading.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    heading.setMarqueeRepeatLimit(-1);
    heading.setSingleLine(true);
    heading.setSelected(true);
    heading.setTextColor(TEXT_COLOR);
    heading.setTextSize(10.0f);
    heading.setGravity(Gravity.CENTER);
    heading.setPadding(0, 0, 0, 5);
    setHeadingText(heading);

    // ********** Mod menu feature list **********
    scrollView = new ScrollView(this);
    // Auto size. To set size manually, change the width and height example 500, 500
    scrlLL = new LinearLayout.LayoutParams(MATCH_PARENT, dp(MENU_HEIGHT));
    scrlLLExpanded = new LinearLayout.LayoutParams(mExpanded.getLayoutParams());
    scrlLLExpanded.weight = 1.0f;
    scrollView.setLayoutParams(Preferences.isExpanded ? scrlLLExpanded : scrlLL);
    scrollView.setBackgroundColor(MENU_FEATURE_BG_COLOR);
    patches = new LinearLayout(this);
    patches.setOrientation(LinearLayout.VERTICAL);

    // ********** RelativeLayout for buttons **********

    // **********  Hide/Kill button **********
    RelativeLayout relativeLayout = new RelativeLayout(this);
    relativeLayout.setPadding(0, 0, 0, 0);
    relativeLayout.setVerticalGravity(Gravity.CENTER);

    // Button hideBtn = new Button(this);
    RelativeLayout.LayoutParams lParamsHideBtn =
        new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    lParamsHideBtn.addRule(ALIGN_PARENT_LEFT);

    final Button hideBtn = new Button(this);
    hideBtn.setLayoutParams(lParamsHideBtn);
    int[] colorsCRNGH = {(RBG), (RBG)};
    android.graphics.drawable.GradientDrawable CRNGH =
        new android.graphics.drawable.GradientDrawable(
            android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNGH);
    CRNGH.setCornerRadii(
        new float[] {(int) 0, (int) 0, (int) 50, (int) 50, (int) 0, (int) 0, (int) 0, (int) 0});
    CRNGH.setStroke((int) 3, Color.DKGRAY);
    hideBtn.setBackground(CRNGH);
    hideBtn.setText("SETTING");
    hideBtn.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    hideBtn.setTextColor(TEXT_COLOR);
    hideBtn.setOnClickListener(
        new View.OnClickListener() {
          boolean settingsOpen;

          @Override
          public void onClick(View v) {
            try {
              settingsOpen = !settingsOpen;
              if (settingsOpen) {
                scrollView.removeView(patches);
                scrollView.addView(mSettings);
                scrollView.scrollTo(0, 0);
              } else {
                scrollView.removeView(mSettings);
                scrollView.addView(patches);
              }
            } catch (IllegalStateException e) {
            }
          }
        });

    // ********** Close button **********
    RelativeLayout.LayoutParams lParamsCloseBtn =
        new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    lParamsCloseBtn.addRule(ALIGN_PARENT_RIGHT);

    Button closeBtn = new Button(this);
    closeBtn.setLayoutParams(lParamsCloseBtn);
    closeBtn.setBackgroundColor(Color.TRANSPARENT);
    int[] colorsCRNPZ = {(RBG), (RBG)};
    android.graphics.drawable.GradientDrawable CRNPZ =
        new android.graphics.drawable.GradientDrawable(
            android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNPZ);
    CRNPZ.setCornerRadii(
        new float[] {(int) 50, (int) 50, (int) 0, (int) 0, (int) 0, (int) 0, (int) 0, (int) 0});
    CRNPZ.setStroke((int) 3, Color.DKGRAY);
    closeBtn.setBackground(CRNPZ);
    closeBtn.setText("MINIMIZE");
    closeBtn.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    closeBtn.setTextColor(TEXT_COLOR);
    closeBtn.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View view) {
            mCollapsed.setVisibility(View.VISIBLE);
            mCollapsed.setAlpha(ICON_ALPHA);
            mExpanded.setVisibility(View.GONE);
          }
        });

    // ********** Params **********
    // Variable to check later if the phone supports Draw over other apps permission
    int iparams = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? 2038 : 2002;
    params = new WindowManager.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, iparams, 8, -3);
    params.gravity = 51;
    params.x = 0;
    params.y = 100;

    // ********** Adding view components **********
    rootFrame.addView(mRootContainer);
    mRootContainer.addView(mCollapsed);
    mRootContainer.addView(mExpanded);
    if (IconWebViewData() != null) {
      mCollapsed.addView(wView);
    } else {
      mCollapsed.addView(startimage);
    }
    titleText.addView(title);
    titleText.addView(settings);
    mExpanded.addView(titleText);
    mExpanded.addView(heading);
    scrollView.addView(patches);
    mExpanded.addView(scrollView);
    relativeLayout.addView(hideBtn);
    relativeLayout.addView(closeBtn);
    mExpanded.addView(relativeLayout);
    mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    mWindowManager.addView(rootFrame, params);

    final Handler handler = new Handler();
    handler.postDelayed(
        new Runnable() {
          boolean viewLoaded = false;

          @Override
          public void run() {
            // If the save preferences is enabled, it will check if game lib is loaded before
            // starting menu
            // Comment the if-else code out except startService if you want to run the app and test
            // preferences
            if (Preferences.loadPref && !isGameLibLoaded() && !stopChecking) {
              if (!viewLoaded) {
                patches.addView(
                    Category(
                        "Save preferences was been enabled. Waiting for game lib to be loaded...\n\nForce load menu may not apply mods instantly. You would need to reactivate them again"));
                patches.addView(Button(-100, "Force load menu"));
                viewLoaded = true;
              }
              handler.postDelayed(this, 600);
            } else {
              patches.removeAllViews();
              featureList(getFeatureList(), patches);
            }
          }
        },
        500);
  }

  private View.OnTouchListener onTouchListener() {
    return new View.OnTouchListener() {
      final View collapsedView = mCollapsed;
      final View expandedView = mExpanded;
      private float initialTouchX, initialTouchY;
      private int initialX, initialY;

      public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
          case MotionEvent.ACTION_DOWN:
            initialX = params.x;
            initialY = params.y;
            initialTouchX = motionEvent.getRawX();
            initialTouchY = motionEvent.getRawY();
            return true;
          case MotionEvent.ACTION_UP:
            int rawX = (int) (motionEvent.getRawX() - initialTouchX);
            int rawY = (int) (motionEvent.getRawY() - initialTouchY);
            mExpanded.setAlpha(1f);
            mCollapsed.setAlpha(1f);
            // The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while
            // clicking.
            // So that is click event.
            if (rawX < 10 && rawY < 10 && isViewCollapsed()) {
              // When user clicks on the image view of the collapsed layout,
              // visibility of the collapsed layout will be changed to "View.GONE"
              // and expanded view will become visible.
              try {
                collapsedView.setVisibility(View.GONE);
                expandedView.setVisibility(View.VISIBLE);
              } catch (NullPointerException e) {

              }
            }
            return true;
          case MotionEvent.ACTION_MOVE:
            mExpanded.setAlpha(0.5f);
            mCollapsed.setAlpha(0.5f);
            // Calculate the X and Y coordinates of the view.
            params.x = initialX + ((int) (motionEvent.getRawX() - initialTouchX));
            params.y = initialY + ((int) (motionEvent.getRawY() - initialTouchY));
            // Update the layout with new X & Y coordinate
            mWindowManager.updateViewLayout(rootFrame, params);
            return true;
          default:
            return false;
        }
      }
    };
  }

  private void featureList(String[] listFT, LinearLayout linearLayout) {
    // Currently looks messy right now. Let me know if you have improvements
    int featNum, subFeat = 0;
    LinearLayout llBak = linearLayout;

    for (int i = 0; i < listFT.length; i++) {
      boolean switchedOn = false;
      // Log.i("featureList", listFT[i]);
      String feature = listFT[i];
      if (feature.contains("True_")) {
        switchedOn = true;
        feature = feature.replaceFirst("True_", "");
      }

      linearLayout = llBak;
      if (feature.contains("CollapseAdd_")) {
        // if (collapse != null)
        linearLayout = mCollapse;
        feature = feature.replaceFirst("CollapseAdd_", "");
      }
      String[] str = feature.split("_");

      // Assign feature number
      if (TextUtils.isDigitsOnly(str[0]) || str[0].matches("-[0-9]*")) {
        featNum = Integer.parseInt(str[0]);
        feature = feature.replaceFirst(str[0] + "_", "");
        subFeat++;
      } else {
        // Subtract feature number. We don't want to count ButtonLink, Category, RichTextView and
        // RichWebView
        featNum = i - subFeat;
      }
      String[] strSplit = feature.split("_");
      switch (strSplit[0]) {
        case "Toggle":
          Switch(linearLayout, featNum, strSplit[1], switchedOn);
          break;
        case "SeekBar":
          linearLayout.addView(
              SeekBar(
                  featNum,
                  strSplit[1],
                  Integer.parseInt(strSplit[2]),
                  Integer.parseInt(strSplit[3])));
          break;
        case "Button":
          linearLayout.addView(Button(featNum, strSplit[1]));
          break;
        case "ButtonOnOff":
          linearLayout.addView(ButtonOnOff(featNum, strSplit[1], switchedOn));
          break;
        case "Spinner":
          linearLayout.addView(RichTextView(strSplit[1]));
          linearLayout.addView(Spinner(featNum, strSplit[1], strSplit[2]));
          break;
        case "InputText":
          linearLayout.addView(TextField(featNum, strSplit[1], false, 0));
          break;
        case "InputValue":
          if (strSplit.length == 3)
            linearLayout.addView(
                TextField(featNum, strSplit[2], true, Integer.parseInt(strSplit[1])));
          if (strSplit.length == 2) linearLayout.addView(TextField(featNum, strSplit[1], true, 0));
          break;
        case "CheckBox":
          linearLayout.addView(CheckBox(featNum, strSplit[1], switchedOn));
          break;
        case "RadioButton":
          RadioButton(linearLayout, featNum, strSplit[1], strSplit[2]);
          break;
        case "Collapse":
          Collapse(linearLayout, strSplit[1]);
          subFeat++;
          break;
        case "ButtonLink":
          subFeat++;
          linearLayout.addView(ButtonLink(strSplit[1], strSplit[2]));
          break;
        case "Category":
          subFeat++;
          linearLayout.addView(Category(strSplit[1]));
          break;
        case "RichTextView":
          subFeat++;
          linearLayout.addView(RichTextView(strSplit[1]));
          break;
        case "RichWebView":
          subFeat++;
          linearLayout.addView(RichWebView(strSplit[1]));
          break;
        case "ChamsShader":
          linearLayout.addView(
              ChamsShader(
                  featNum,
                  strSplit[1],
                  Integer.parseInt(strSplit[2]),
                  Integer.parseInt(strSplit[3])));
          break;
        case "ColorPicker":
          linearLayout.addView(
              ColorPicker(
                  featNum,
                  strSplit[1],
                  Integer.parseInt(strSplit[2]),
                  Integer.parseInt(strSplit[3]),
                  Integer.parseInt(strSplit[4]),
                  Integer.parseInt(strSplit[5])));
          break;
        case "ColorC":
          Color1(
              linearLayout,
              featNum,
              strSplit[1],
              Integer.parseInt(strSplit[2]),
              Integer.parseInt(strSplit[3]),
              Integer.parseInt(strSplit[4]),
              Integer.parseInt(strSplit[5]));
          break;
      }
    }
  }

  private View Color1(
      LinearLayout data,
      final int featNum,
      final String featName,
      final int alpha,
      final int red,
      final int green,
      final int blue) {
    final int[] initialProgress = new int[] {alpha, red, green, blue};

    final GradientDrawable btn = new GradientDrawable();
    btn.setOrientation(Orientation.TL_BR);
    btn.setStroke(1, Color.DKGRAY);
    btn.setColors(
        new int[] {
          Color.WHITE,
          Color.argb(initialProgress[0], initialProgress[1], initialProgress[2], initialProgress[3])
        });
    btn.setCornerRadius(5f);
    btn.setSize(dp(10), dp(12));
    btn.setTintMode(PorterDuff.Mode.MULTIPLY);

    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    layoutParams.setMargins(7, 5, 7, 5);
    linh = new LinearLayout(this);
    linh.setOrientation(1);
    linh.setPadding(5, 5, 5, 5);

    HorizontalScrollView scrollh = new HorizontalScrollView(this);
    scrollh.setEnabled(true);
    scrollh.setHorizontalScrollBarEnabled(false);
    tablayoutButton = new LinearLayout(this);
    tablayoutButton.setOrientation(0);

    TextView textView = new TextView(this);
    textView.setTextColor(TextColor);
    textView.setText(featName);
    textView.setTypeface(Typeface.SERIF, 1);
    textView.setLayoutParams(layoutParams);
    textView.setTextSize(12.0F);
    // textView.setX(-160);
    textView.setGravity(Gravity.CENTER);
    textView.setPadding(5, 5, 5, 5);

    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(dp(20), dp(20));
    layoutParams1.setMargins(15, 5, 7, 5);
    final View colorView = new View(this);
    colorView.setLayoutParams(layoutParams1);
    Color(
        colorView,
        Color.argb(initialProgress[0], initialProgress[1], initialProgress[2], initialProgress[3]),
        5,
        1,
        Color.WHITE);
    colorView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            final AlertDialog alert = new AlertDialog.Builder(getApplicationContext(), 2).create();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
              Objects.requireNonNull(alert.getWindow())
                  .setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
            }
            alert.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                  public void onCancel(DialogInterface dialog) {
                    InputMethodManager imm =
                        (InputMethodManager)
                            getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                  }
                });
            GradientDrawable killshot = new GradientDrawable();
            killshot.setColor(Color.parseColor("#000000"));
            //  killshot.setStroke(4, Color.WHITE);

            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
            relativeLayout.setPadding(10, 5, 10, 5);
            relativeLayout.setVerticalGravity(Gravity.CENTER);
            relativeLayout.setBackgroundDrawable(killshot);

            RelativeLayout.LayoutParams lParamsHideBtn =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            // lParamsHideBtn.setMargins(230,0,0,0);
            lParamsHideBtn.addRule(ALIGN_PARENT_LEFT);

            RelativeLayout.LayoutParams lParamsHideBt =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            lParamsHideBt.addRule(ALIGN_PARENT_RIGHT);
            // LinearLayout
            final LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
            linearLayout1.setPadding(5, 5, 5, 5);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            Color(linearLayout1, Color.parseColor("#403E45"), 15, 2, Color.WHITE);
            linearLayout1.setLayoutParams(
                new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            final GradientDrawable seekbarCircle = new GradientDrawable();
            int BEBCBGDADD[] = new int[] {Color.WHITE, Color.BLACK};
            seekbarCircle.setCornerRadius(5.0f);
            seekbarCircle.setSize(dp(6), dp(18));
            seekbarCircle.setStroke(1, Color.parseColor("#ffffff"));
            seekbarCircle.setColors(BEBCBGDADD);
            seekbarCircle.setOrientation(GradientDrawable.Orientation.TR_BL);
            final RippleDrawable BEBCBGD_RE =
                new RippleDrawable(
                    new ColorStateList(new int[][] {new int[] {}}, new int[] {Color.BLUE}),
                    seekbarCircle,
                    null);

            // TextView
            final TextView titleText = new TextView(getApplicationContext());
            titleText.setText(
                Html.fromHtml(
                    new StringBuffer()
                        .append(new StringBuffer().append("<u>").append(featName).toString())
                        .append("</u>")
                        .toString()));
            titleText.setGravity(Gravity.CENTER);
            titleText.setTextColor(TextColor);
            titleText.setTextSize(18f);
            titleText.setTypeface(Typeface.DEFAULT_BOLD);
            /// Colors
            final TextView Close = new TextView(getApplicationContext());
            Close.setText("➦");
            Close.setTextColor(Color.RED);
            Close.setTextSize(18);
            Close.setLayoutParams(lParamsHideBt);
            Close.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    alert.dismiss();
                  }
                });
            LayoutParams layoutParm = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            layoutParm.setMargins(20, 10, 20, 10);

            RelativeLayout relativeLayout2 = new RelativeLayout(getApplicationContext());
            relativeLayout2.setVerticalGravity(Gravity.CENTER);
            relativeLayout2.setLayoutParams(layoutParm);

            RelativeLayout.LayoutParams Left =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(40));
            Left.setMargins(5, 5, 5, 5);
            Left.addRule(ALIGN_PARENT_LEFT);

            RelativeLayout.LayoutParams center =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(40));
            center.setMargins(5, 5, 5, 5);
            center.addRule(RelativeLayout.CENTER_IN_PARENT);
            RelativeLayout.LayoutParams center1 =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            center1.setMargins(5, 5, 5, 5);
            center1.addRule(RelativeLayout.CENTER_IN_PARENT);

            RelativeLayout.LayoutParams Right =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(40));
            Right.addRule(ALIGN_PARENT_RIGHT);
            Right.setMargins(5, 5, 5, 5);

            LinearLayout TestLinear = new LinearLayout(MenuV5.this);
            TestLinear.setOrientation(LinearLayout.HORIZONTAL);
            TestLinear.setPadding(5, 5, 5, 5);
            final TextView colorTextView2 = new TextView(getApplicationContext());
            // colorTextView2.setText(("#" + String.format("%02x", new Object[]{new Integer(alpha)})
            // + String.format("%02x", new Object[]{new Integer(red)}) + String.format("%02x", new
            // Object[]{new Integer(green)}) + String.format("%02x", new Object[]{new
            // Integer(blue)})).toUpperCase());
            colorTextView2.setText(
                ("Hex::#"
                        + String.format("%02x", new Object[] {new Integer(initialProgress[0])})
                        + String.format("%02x", new Object[] {new Integer(initialProgress[1])})
                        + String.format("%02x", new Object[] {new Integer(initialProgress[2])})
                        + String.format("%02x", new Object[] {new Integer(initialProgress[3])}))
                    .toUpperCase());
            colorTextView2.setTextColor(TextColor);
            colorTextView2.setGravity(Gravity.CENTER);
            colorTextView2.setTextIsSelectable(true);
            colorTextView2.setPadding(15, 5, 15, 5);
            colorTextView2.setTextSize(15f);
            Color(colorTextView2, Color.parseColor("#474448"), 15, 1, Color.WHITE);
            colorTextView2.setLayoutParams(center1);

            final Button copy = new Button(getApplicationContext());
            Color(copy, Color.parseColor("#FAFAFA"), 20, 2, Color.parseColor("#ACADB1"));
            copy.setTextColor(Color.parseColor("#405888"));
            copy.setGravity(Gravity.CENTER);
            copy.setTextSize(16);
            copy.setPadding(5, 5, 5, 5);
            copy.setText("\u274F Copy");
            copy.setLayoutParams(Left);
            copy.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    ClipboardManager clipboard =
                        (ClipboardManager)
                            getApplicationContext()
                                .getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(null, colorTextView2.getText());
                    clipboard.setPrimaryClip(clip);
                    copy.setText("\u2713 Copied!");

                    Toast.makeText(view.getContext(), "Key Copied To ClipBoard", Toast.LENGTH_LONG)
                        .show();
                    new Handler()
                        .postDelayed(
                            new Runnable() {
                              @Override
                              public void run() {
                                // Perform the delayed task here
                                copy.setText("\u274F Copy");
                              }
                            },
                            2000);
                  }
                });

            RelativeLayout relativeLayout1 = new RelativeLayout(getApplicationContext());
            relativeLayout1.setVerticalGravity(Gravity.CENTER);
            relativeLayout1.setBackgroundColor(Color.TRANSPARENT);
            relativeLayout1.setPadding(5, 3, 5, 3);
            RelativeLayout.LayoutParams left = new RelativeLayout.LayoutParams(dp(30), dp(20));
            left.addRule(ALIGN_PARENT_LEFT);

            RelativeLayout.LayoutParams right = new RelativeLayout.LayoutParams(dp(30), dp(20));
            right.addRule(ALIGN_PARENT_RIGHT);
            RelativeLayout.LayoutParams Center =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(20));
            Center.addRule(CENTER_HORIZONTAL);

            final View colorNew = new View(getApplicationContext());
            colorNew.setPadding(5, 5, 15, 5);
            Shap(
                colorNew,
                Color.argb(
                    initialProgress[0], initialProgress[1], initialProgress[2], initialProgress[3]),
                15,
                1,
                2,
                Color.WHITE,
                0,
                0);

            colorNew.setLayoutParams(right);
            final View colorview2 = new View(getApplicationContext());
            colorview2.setLayoutParams(left);
            colorview2.setPadding(15, 5, 5, 5);
            // colorview2.setBackgroundDrawable(Orginal);
            Shap(
                colorview2,
                Color.argb(
                    initialProgress[0], initialProgress[1], initialProgress[2], initialProgress[3]),
                15,
                1,
                2,
                Color.WHITE,
                0,
                0);

            final LinearLayout seekLayout = new LinearLayout(getApplicationContext());
            seekLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
            seekLayout.setPadding(10, 0, 0, 0);
            seekLayout.setOrientation(LinearLayout.VERTICAL);

            final SeekBar[] seekBarArr = new SeekBar[1];
            final CircularHSVSeekBar colorPickerView = new CircularHSVSeekBar(MenuV5.this);
            colorPickerView.setLayoutParams(new LayoutParams(dp(385), dp(180)));
            colorPickerView.setColor(
                Color.argb(
                    initialProgress[0],
                    initialProgress[1],
                    initialProgress[2],
                    initialProgress[3]));
            colorPickerView.setOnColorSelectedListener(
                new CircularHSVSeekBar.OnColorSelectedListener() {
                  @Override
                  public void onColorSelected(int color) {
                    // Update the background or other UI elements
                    final int r = Color.red(color);
                    final int g = Color.green(color);
                    final int b = Color.blue(color);
                    final int a = seekBarArr[0].getProgress();
                    final int bg = Color.argb(a, r, g, b);
                    currentColor = color;
                    // Draw the circle on the overlay

                    colorTextView2.setText(
                        ("Hex::#"
                                + String.format("%02x", new Object[] {new Integer(a)})
                                + String.format("%02x", new Object[] {new Integer(r)})
                                + String.format("%02x", new Object[] {new Integer(g)})
                                + String.format("%02x", new Object[] {new Integer(b)}))
                            .toUpperCase());
                    Shap(colorNew, bg, 15, 1, 2, Color.WHITE, 0, 0);
                    btn.setColors(new int[] {Color.WHITE, bg});
                  }
                });
            for (int i = 0; i < 1; i++) {
              LinearLayout TestLinear3 = new LinearLayout(MenuV5.this);
              TestLinear3.setOrientation(LinearLayout.HORIZONTAL);
              TestLinear3.setPadding(5, 5, 5, 5);

              final SimpleVerticalSeekBar transparencySeekBar =
                  new SimpleVerticalSeekBar(MenuV5.this);
              // final  SeekBar transparencySeekBar = new SeekBar(FloatingModMenuService.this);
              transparencySeekBar.setMax(255); // Set the maximum alpha value
              transparencySeekBar.setProgress(initialProgress[i]); // Default to fully opaque
              transparencySeekBar.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
              // transparencySeekBar.setRotation(260);
              transparencySeekBar.setPaddingRelative(10, 25, 50, 2);
              transparencySeekBar.setThumb(BEBCBGD_RE);
              transparencySeekBar.setProgressDrawable(btn);
              transparencySeekBar.setOnSeekBarChangeListener(
                  new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                      // currentAlpha[0] = i;
                      // int colorWithAlpha = (currentAlpha[0] << 24) | (currentColor & 0x00FFFFFF);
                      int r = Color.red(currentColor);
                      int g = Color.green(currentColor);
                      int b = Color.blue(currentColor);
                      int a = seekBarArr[0].getProgress();
                      int bg = Color.argb(a, r, g, b);
                      // int a =Color.alpha(colorWithAlpha);
                      for (int h = 0; h < 4; h++) {
                        final int[] initialProgress = new int[] {a, r, g, b};
                        // colorRbgText[h].setText(Html.fromHtml(strArr[h]+":<font color='" +
                        // NumberTxtColor + "'>" +initialProgress[h]));
                      }
                      Shap(colorNew, bg, 15, 1, 2, Color.WHITE, 0, 0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                  });

              TestLinear3.addView(colorPickerView);
              TestLinear3.addView(transparencySeekBar);
              seekLayout.addView(TestLinear3);
              seekBarArr[i] = transparencySeekBar;
            }

            Button btndialog = new Button(getApplicationContext());
            Color(btndialog, Color.parseColor("#FAFAFA"), 20, 2, Color.parseColor("#ACADB1"));
            btndialog.setTextColor(Color.parseColor("#405888"));
            btndialog.setGravity(Gravity.CENTER);
            btndialog.setTextSize(16);
            btndialog.setPadding(5, 5, 5, 5);
            btndialog.setText("Done");
            btndialog.setLayoutParams(Right);
            btndialog.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    //  int colorWithAlpha = (currentAlpha[0] << 24) | (currentColor & 0x00FFFFFF);
                    final int intred = Color.red(currentColor);
                    final int intgreen = Color.green(currentColor);
                    final int intblue = Color.blue(currentColor);
                    final int intalpha = seekBarArr[0].getProgress();
                    initialProgress[0] = intalpha;
                    initialProgress[1] = intred;
                    initialProgress[2] = intgreen;
                    initialProgress[3] = intblue;

                    final int bg = Color.argb(intalpha, intred, intgreen, intblue);
                    for (int i = 0; i < 1; i++) {
                      initialProgress[i] = seekBarArr[i].getProgress();
                    }

                    Shap(colorview2, bg, 15, 1, 2, Color.WHITE, 0, 0);
                    Shap(colorNew, bg, 15, 1, 2, Color.WHITE, 0, 0);
                    Color(colorView, bg, 5, 1, Color.WHITE);

                    colorTextView2.setText(
                        ("Hex::#"
                                + String.format("%02x", new Object[] {new Integer(intalpha)})
                                + String.format("%02x", new Object[] {new Integer(intred)})
                                + String.format("%02x", new Object[] {new Integer(intgreen)})
                                + String.format("%02x", new Object[] {new Integer(intblue)}))
                            .toUpperCase());
                    btn.setColors(
                        new int[] {Color.WHITE, Color.argb(intalpha, intred, intgreen, intblue)});
                    switch (featNum) {
                    }
                    Preferences.changeFeatureColor(
                        featName, featNum, intalpha, intred, intgreen, intblue);
                  }
                });
            relativeLayout.addView(titleText);
            relativeLayout.addView(Close);
            linearLayout1.addView(relativeLayout);
            linearLayout1.addView(seekLayout);
            relativeLayout1.addView(colorview2);
            relativeLayout1.addView(colorTextView2);
            relativeLayout1.addView(colorNew);
            linearLayout1.addView(relativeLayout1);

            relativeLayout2.addView(copy);

            relativeLayout2.addView(btndialog);
            linearLayout1.addView(relativeLayout2);
            alert.setView(linearLayout1);
            alert.setCanceledOnTouchOutside(false);
            alert.show();
          }
        });
    linh.addView(scrollh);
    tablayoutButton.addView(colorView);
    tablayoutButton.addView(textView);
    // tablayoutButton.addView(colorTextView);
    scrollh.addView(tablayoutButton);
    data.addView(linh);
    return linh;
  }

  private View ColorPicker(
      final int featNum,
      final String featName,
      final int alpha,
      final int red,
      final int green,
      final int blue) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    layoutParams.setMargins(7, 5, 7, 5);
    linh = new LinearLayout(this);

    linh.setOrientation(1);
    linh.setPadding(5, 5, 5, 5);

    LinearLayout linearLayouti = new LinearLayout(this);
    // linearLayouti.setBackgroundColor(-1);
    linearLayouti.setPadding(-1, 2, -1, 1);

    LinearLayout linearLayout2i = new LinearLayout(this);
    // linearLayout2i.setBackgroundColor(-1);
    linearLayout2i.setPadding(-1, 2, -1, 1);

    HorizontalScrollView scrollh = new HorizontalScrollView(this);
    scrollh.setEnabled(true);
    scrollh.setHorizontalScrollBarEnabled(false);
    tablayoutButton = new LinearLayout(this);
    tablayoutButton.setOrientation(0);

    final int[] initialProgress = new int[] {alpha, red, green, blue};

    final TextView colorTextView = new TextView(this);
    GradientDrawable T = new GradientDrawable();
    T.setColor(Color.parseColor("#B03AB7"));
    T.setCornerRadius(25);
    colorTextView.setBackgroundDrawable(T);
    colorTextView.setText(
        ("#"
                + String.format("%02x", new Object[] {new Integer(alpha)})
                + String.format("%02x", new Object[] {new Integer(red)})
                + String.format("%02x", new Object[] {new Integer(green)})
                + String.format("%02x", new Object[] {new Integer(blue)}))
            .toUpperCase());
    colorTextView.setTextColor(Color.DKGRAY);
    colorTextView.setTextSize(10.0F);
    // colorTextView.setGravity(3);
    colorTextView.setPadding(10, 5, 10, 5);
    colorTextView.setLayoutParams(layoutParams);

    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(dp(20), dp(20));
    layoutParams1.setMargins(15, 5, 7, 5);
    final View colorView = new View(this);
    colorView.setLayoutParams(layoutParams1);
    final GradientDrawable Color1 = new GradientDrawable();
    Color1.setColor(Color.argb(alpha, red, green, blue));
    Color1.setCornerRadius(5f);
    Color1.setStroke(1, Color.parseColor("#ff000000"));
    colorView.setBackground(Color1);
    // colorView.setLayoutParams(new LayoutParams(dp(20), dp(10)));
    // colorView.setBackgroundColor(Color.argb(alpha, red, green, blue));
    colorView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            final AlertDialog alert = new AlertDialog.Builder(getApplicationContext(), 2).create();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
              Objects.requireNonNull(alert.getWindow())
                  .setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
            }
            alert.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                  public void onCancel(DialogInterface dialog) {
                    InputMethodManager imm =
                        (InputMethodManager)
                            getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                  }
                });
            GradientDrawable killshot = new GradientDrawable();
            killshot.setColor(Color.parseColor("#000000"));
            //  killshot.setStroke(4, Color.WHITE);

            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
            relativeLayout.setPadding(10, 5, 10, 5);
            relativeLayout.setVerticalGravity(Gravity.CENTER);
            relativeLayout.setBackgroundDrawable(killshot);

            RelativeLayout.LayoutParams lParamsHideBtn =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            // lParamsHideBtn.setMargins(230,0,0,0);
            lParamsHideBtn.addRule(ALIGN_PARENT_LEFT);

            RelativeLayout.LayoutParams lParamsHideBt =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            lParamsHideBt.addRule(ALIGN_PARENT_RIGHT);
            // LinearLayout
            LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
            linearLayout1.setPadding(5, 5, 5, 5);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            GradientDrawable gdLayout2 = new GradientDrawable();
            gdLayout2.setColor(Color.parseColor("#9C9C9C"));
            gdLayout2.setStroke(3, Color.WHITE);
            linearLayout1.setBackground(gdLayout2);

            // TextView
            final TextView titleText = new TextView(getApplicationContext());
            titleText.setText(
                Html.fromHtml(
                    new StringBuffer()
                        .append(new StringBuffer().append("<u>").append(featName).toString())
                        .append("</u>")
                        .toString()));
            titleText.setGravity(Gravity.CENTER);
            titleText.setTextColor(Color.DKGRAY);
            titleText.setTextSize(18f);
            titleText.setTypeface(Typeface.DEFAULT_BOLD);
            /// Colors
            RelativeLayout.LayoutParams G = new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(70));
            G.setMargins(5, 5, 5, 5);
            // G.addRule(ALIGN_PARENT_LEFT);

            RelativeLayout relativeLayout1 = new RelativeLayout(getApplicationContext());
            relativeLayout1.setPadding(10, 15, 1, 10);
            relativeLayout1.setVerticalGravity(Gravity.CENTER);
            relativeLayout1.setBackgroundColor(Color.TRANSPARENT);
            relativeLayout1.setLayoutParams(G);

            RelativeLayout.LayoutParams Left = new RelativeLayout.LayoutParams(dp(60), dp(60));
            Left.setMargins(5, 5, 5, 5);
            Left.addRule(ALIGN_PARENT_LEFT);

            RelativeLayout.LayoutParams Right = new RelativeLayout.LayoutParams(dp(30), dp(30));
            Right.addRule(ALIGN_PARENT_RIGHT);
            Right.setMargins(5, 1, 5, 5);

            RelativeLayout.LayoutParams Center =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(70));
            Center.addRule(ALIGN_PARENT_RIGHT);
            Center.setMargins(5, 1, 5, 5);
            RelativeLayout.LayoutParams Center1 =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, dp(70));
            Center1.addRule(ALIGN_PARENT_RIGHT);
            Center1.setMargins(5, 1, 5, 5);
            LinearLayout TestLinear = new LinearLayout(MenuV5.this);
            TestLinear.setOrientation(LinearLayout.VERTICAL);
            TestLinear.setPadding(5, 5, 5, 5);

            final View colorviewO = new View(getApplicationContext());
            colorviewO.setPadding(5, 5, 5, 5);
            colorviewO.setLayoutParams(Right);
            GradientDrawable desig = new GradientDrawable();
            desig.setColor(
                Color.argb(
                    initialProgress[0],
                    initialProgress[1],
                    initialProgress[2],
                    initialProgress[3]));
            desig.setCornerRadius(10f);
            // design.setSize(dp(50),dp(180));
            desig.setStroke(2, Color.DKGRAY);
            colorviewO.setBackgroundDrawable(desig);

            final View colorview2 = new View(getApplicationContext());
            colorview2.setLayoutParams(new LayoutParams(dp(100), dp(100)));
            //	colorview2.setBackgroundColor(Color.argb(initialProgress[0], initialProgress[1],
            // initialProgress[2], initialProgress[3]));
            final GradientDrawable design =
                new GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP,
                    new int[] {
                      Color.BLACK,
                      Color.argb(
                          initialProgress[0],
                          initialProgress[1],
                          initialProgress[2],
                          initialProgress[3])
                    });
            // design.setColor(Color.argb(initialProgress[0], initialProgress[1],
            // initialProgress[2], initialProgress[3]));
            design.setCornerRadius(5f);
            design.setShape(1);
            // design.setSize(dp(50),dp(180));
            design.setStroke(2, Color.DKGRAY);
            colorview2.setBackgroundDrawable(design);
            colorview2.setLayoutParams(Left);

            final TextView colorTextView2 = new TextView(getApplicationContext());
            colorTextView2.setText(
                ("#"
                        + String.format("%02x", new Object[] {new Integer(initialProgress[0])})
                        + String.format("%02x", new Object[] {new Integer(initialProgress[1])})
                        + String.format("%02x", new Object[] {new Integer(initialProgress[2])})
                        + String.format("%02x", new Object[] {new Integer(initialProgress[3])}))
                    .toUpperCase());
            colorTextView2.setTextColor(Color.DKGRAY);
            colorTextView2.setGravity(Gravity.RIGHT);
            colorTextView2.setTextIsSelectable(true);
            colorTextView2.setPadding(0, 110, 1, 10);
            colorTextView2.setTextSize(20f);
            // colorTextView2.setX(280);
            colorTextView2.setLayoutParams(Center);

            final TextView Original = new TextView(getApplicationContext());
            Original.setText("Orignal\nColor");
            Original.setTextColor(Color.BLACK);
            Original.setTypeface(Typeface.SERIF, 1);
            Original.setLayoutParams(Center1);
            Original.setTextSize(16);
            // Original.setY(-45);
            Original.setX(-105);
            Original.setPadding(5, -10, 5, 5);
            // Original.setLayoutParams(new LayoutParams(dp(60),dp(60)));
            //	col
            // Original.setLayoutParams(LineTopPara);

            final TextView copy = new TextView(getApplicationContext());
            copy.setText("\u274F Copy");
            copy.setTextColor(Color.DKGRAY);
            copy.setTextSize(15);
            copy.setLayoutParams(lParamsHideBt);
            copy.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                    ClipboardManager clipboard =
                        (ClipboardManager)
                            getApplicationContext()
                                .getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(null, colorTextView2.getText());
                    clipboard.setPrimaryClip(clip);
                    copy.setText("\u2713 Copied!");

                    Toast.makeText(view.getContext(), "Key Copied To ClipBoard", Toast.LENGTH_LONG)
                        .show();
                    new Handler()
                        .postDelayed(
                            new Runnable() {
                              @Override
                              public void run() {
                                // Perform the delayed task here
                                copy.setText("\u274F Copy");
                              }
                            },
                            2000);
                  }
                });

            LinearLayout seekLayout = new LinearLayout(getApplicationContext());
            seekLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            seekLayout.setPadding(10, 0, 0, 0);
            seekLayout.setOrientation(1);

            final String[] strArr = new String[] {"T:", "R:", "G:", "B:"};
            final SeekBar[] seekBarArr = new SeekBar[4];
            int[] iArr = new int[] {Color.WHITE, Color.RED, Color.GREEN, Color.BLUE};
            for (int i = 0; i < 4; i++) {
              LinearLayout linearLayout3 = new LinearLayout(getApplicationContext());
              linearLayout3.setOrientation(0);
              linearLayout3.setPadding(5, 10, 5, 10);
              linearLayout3.setBackgroundColor(Color.parseColor("#9C9C9C"));
              // linearLayout3.setGravity(17);

              final TextView textView3 = new TextView(getApplicationContext());
              textView3.setText(
                  new StringBuffer()
                      .append(
                          new StringBuffer()
                              .append(
                                  new StringBuffer()
                                      .append(strArr[i])
                                      .append(initialProgress[i]))));
              textView3.setTextColor(Color.WHITE);
              textView3.setGravity(Gravity.LEFT);
              textView3.setPadding(5, 5, 1, 5);
              final GradientDrawable design1 = new GradientDrawable();
              // design.setColor(Color.argb(initialProgress[0], initialProgress[1],
              // initialProgress[2], initialProgress[3]));
              design1.setCornerRadius(5f);
              //	design.setShape(1);
              // design.setSize(dp(50),dp(180));
              design1.setStroke(3, Color.BLACK);
              textView3.setLayoutParams(new LayoutParams(dp(40), -2));

              textView3.setBackgroundDrawable(design1);

              SeekBar seekBar = new SeekBar(getApplicationContext());
              seekBar.setLayoutParams(new LayoutParams(-1, -2));
              seekBar.setMax(255);
              seekBar.setProgress(initialProgress[i]);
              seekBar
                  .getProgressDrawable()
                  .setColorFilter(new PorterDuffColorFilter(iArr[i], PorterDuff.Mode.MULTIPLY));
              seekBar.getThumb().setColorFilter(iArr[i], PorterDuff.Mode.SRC_IN);
              final int xg = i;
              seekBar.setOnSeekBarChangeListener(
                  new OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
                      int alpha = seekBarArr[0].getProgress();
                      int red = seekBarArr[1].getProgress();
                      int green = seekBarArr[2].getProgress();
                      int blue = seekBarArr[3].getProgress();
                      int bg = Color.argb(alpha, red, green, blue);
                      // design.setColor(bg);
                      design.setColors(new int[] {Color.BLACK, bg});

                      // design =  new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new
                      // int[] {Color.BLACK,bg});

                      colorTextView2.setText(
                          ("#"
                                  + String.format("%02x", new Object[] {new Integer(alpha)})
                                  + String.format("%02x", new Object[] {new Integer(red)})
                                  + String.format("%02x", new Object[] {new Integer(green)})
                                  + String.format("%02x", new Object[] {new Integer(blue)}))
                              .toUpperCase());
                      textView3.setText(strArr[xg] + "" + p2);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar p1) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar p1) {}
                  });
              linearLayout3.addView(textView3);
              linearLayout3.addView(seekBar);
              seekLayout.addView(linearLayout3);
              seekBarArr[i] = seekBar;
            }
            // Button
            LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            layoutParams.setMargins(20, 10, 20, 15);

            Button btndialog = new Button(getApplicationContext());
            btndialog.setLayoutParams(layoutParams);
            GradientDrawable gdBtnDialog = new GradientDrawable();
            gdBtnDialog.setColor(Color.argb(40, 0, 0, 0));
            gdBtnDialog.setCornerRadii(new float[] {30, 30, 0, 0, 30, 30, 0, 0});
            gdBtnDialog.setStroke(3, Color.WHITE, 0, 0);
            btndialog.setBackground(gdBtnDialog);
            btndialog.setTextColor(Color.DKGRAY);
            btndialog.setTextSize(16);
            btndialog.setPadding(15, 10, 15, 10);
            btndialog.setText("ENTER");
            btndialog.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    int intAlpha = seekBarArr[0].getProgress();
                    int intRed = seekBarArr[1].getProgress();
                    int intGreen = seekBarArr[2].getProgress();
                    int intBlue = seekBarArr[3].getProgress();
                    for (int i = 0; i < 4; i++) {
                      initialProgress[i] = seekBarArr[i].getProgress();
                    }
                    int bg = Color.argb(intAlpha, intRed, intGreen, intBlue);
                    Color1.setColor(bg);
                    Color1.setStroke(1, Color.parseColor("#ff000000"));
                    colorTextView.setText(
                        ("#"
                                + String.format("%02x", new Object[] {new Integer(intAlpha)})
                                + String.format("%02x", new Object[] {new Integer(intRed)})
                                + String.format("%02x", new Object[] {new Integer(intGreen)})
                                + String.format("%02x", new Object[] {new Integer(intBlue)}))
                            .toUpperCase());
                    // Preferences.changeFeatureColor(featName, featNum, intAlpha, intRed, intGreen,
                    // intBlue);
                    Preferences.changeFeatureColor(featName, featNum, 255, 255, 255, 255);
                    switch (featNum) {
                      case -98:
                        Backgroundscorll.setColor(bg);
                        Backgroundscorllpage.setColor(bg);
                        break;
                      case -99:
                        gdtabChecked.setColor(bg);
                        CRNHVM.setColor(bg);
                        break;
                    }
                    alert.dismiss();
                  }
                });
            relativeLayout.addView(titleText);
            relativeLayout.addView(copy);
            linearLayout1.addView(relativeLayout);
            // linearLayout1.addView(titleText);
            relativeLayout1.addView(colorTextView2);
            relativeLayout1.addView(colorview2);
            relativeLayout1.addView(colorviewO);
            relativeLayout1.addView(Original);
            linearLayout1.addView(relativeLayout1);

            // TestLinear.addView(colorviewO);
            // TestLinear.addView(colorTextView2);
            linearLayout1.addView(seekLayout);
            linearLayout1.addView(btndialog);
            alert.setView(linearLayout1);
            alert.show();
          }
        });
    linh.addView(linearLayouti);
    linh.addView(scrollh);
    tablayoutButton.addView(colorView);

    // tablayoutButton.addView(colorTextView);
    scrollh.addView(tablayoutButton);
    linh.addView(linearLayout2i);

    return linh;
  }

  private View ChamsShader(final int featNum, final String featName, final int min, int max) {
    int loadedProg = Preferences.loadPrefInt(featName, featNum);
    LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setPadding(10, 5, 0, 5);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.setGravity(Gravity.CENTER);
    final TextView textView = new TextView(this);
    textView.setText(
        Html.fromHtml(featName + " -> <font color='" + "#FFFF0000" + "'>" + "DEFAULT"));
    textView.setTextColor(Color.RED);
    textView.setTypeface(null, Typeface.BOLD);
    textView.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    ObjectAnimator colorAnimator =
        ObjectAnimator.ofArgb(
            textView, "textColor", Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA);
    colorAnimator.setDuration(2000); // Duraci贸n de la animaci贸n en milisegundos
    colorAnimator.setRepeatCount(
        ObjectAnimator.INFINITE); // Repetir infinitamente (puedes ajustar seg煤n tus necesidades)
    colorAnimator.setRepeatMode(ObjectAnimator.REVERSE); // Invertir la animaci贸n al repetir
    colorAnimator.start();
    SeekBar seekBar = new SeekBar(this);
    GradientDrawable GD_THUMB = new GradientDrawable();
    GD_THUMB.setColor(Color.TRANSPARENT);
    GD_THUMB.setStroke(dp(2), Color.parseColor("#FFFF0000"));
    GD_THUMB.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
    GD_THUMB.setCornerRadii(new float[] {16, 16, 0, 0, 16, 16, 0, 0});
    GD_THUMB.setSize(50, 50);
    seekBar.setPadding(25, 10, 35, 10);
    seekBar.setThumb(GD_THUMB);
    seekBar.setPadding(25, 10, 35, 10);
    seekBar.setMax(max);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
      seekBar.setMin(min); // setMin for Oreo and above
    seekBar.setProgress((loadedProg == 0) ? min : loadedProg);
    seekBar.getThumb().setColorFilter(SeekBarColor, PorterDuff.Mode.SRC_ATOP);
    seekBar.getProgressDrawable().setColorFilter(SeekBarProgressColor, PorterDuff.Mode.SRC_ATOP);
    seekBar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          public void onStartTrackingTouch(SeekBar seekBar) {}

          public void onStopTrackingTouch(SeekBar seekBar) {}

          int l;

          public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (l < i) {
              // playSound(Uri.fromFile(new File(cacheDir + "SliderIncrease.ogg")));
            } else {
              // playSound(Uri.fromFile(new File(cacheDir + "SliderDecrease.ogg")));
            }
            l = i;
            seekBar.setProgress(i < min ? min : i);
            Preferences.changeFeatureInt(featName, featNum, i < min ? min : i);
            if (i == 0) {
              textView.setText(Html.fromHtml(featName + " -> <font color='" + "'>" + "DEFAULT"));
            } else {
              textView.setText(
                  Html.fromHtml(
                      featName + " -> <font color='" + "'>" + (i < min ? min : i) + "(:️"));
            }
          }
        });
    linearLayout.addView(textView);
    linearLayout.addView(seekBar);
    return linearLayout;
  }

  private void Switch(
      LinearLayout linLayout, final int featNum, final String featName, boolean swiOn) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    layoutParams.setMargins(8, 5, 8, 5);
    ;
    final Switch switchR = new Switch(this);
    switchR.setTextSize(15f);
    switchR.setLayoutParams(layoutParams);
    switchR.setText(featName);
    switchR.setTextColor(TEXT_COLOR_2);
    switchR.setPadding(6, 5, 6, 5);
    switchR.setTypeface(Typeface.DEFAULT_BOLD);
    switchR.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    final GradientDrawable GD_TRACK = new GradientDrawable();
    GD_TRACK.setSize(30, 30);
    GD_TRACK.setCornerRadius(100);

    final GradientDrawable GD_THUMB = new GradientDrawable();
    GD_THUMB.setSize(45, 45);
    GD_THUMB.setShape(1);

    if (swiOn) {
      GD_TRACK.setStroke(2, Color.RED);
      GD_TRACK.setColor((RBG));

      GD_THUMB.setStroke(2, Color.RED);
      GD_THUMB.setColor(RBG);
    } else {
      GD_TRACK.setStroke(1, RBG);
      GD_TRACK.setColor(Color.DKGRAY);

      GD_THUMB.setStroke(1, RBG);
      GD_THUMB.setColor(Color.DKGRAY);
    }
    switchR.setThumbDrawable(GD_THUMB);
    switchR.setTrackDrawable(GD_TRACK);
    switchR.setChecked(Preferences.loadPrefBool(featName, featNum, swiOn));
    switchR.setOnCheckedChangeListener(
        new CompoundButton.OnCheckedChangeListener() {
          public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
            Preferences.changeFeatureBool(featName, featNum, bool);
            if (bool) {
              GD_TRACK.setStroke(2, Color.RED);
              GD_TRACK.setColor(RBG);

              GD_THUMB.setStroke(2, Color.RED);
              GD_THUMB.setColor(RBG);
            } else {
              GD_TRACK.setStroke(2, RBG);
              GD_TRACK.setColor(Color.DKGRAY);

              GD_THUMB.setStroke(2, RBG);
              GD_THUMB.setColor(Color.DKGRAY);
            }

            switch (featNum) {
              case -1: // Save perferences
                Preferences.with(switchR.getContext()).writeBoolean(-1, bool);
                if (bool == false)
                  Preferences.with(switchR.getContext())
                      .clear(); // Clear perferences if switched off
                break;
              case -2:
                Preferences.isExpanded = bool;
                scrollView.setLayoutParams(bool ? scrlLLExpanded : scrlLL);
                break;
            }
          }
        });

    linLayout.addView(switchR);
  }

  private View SeekBar(final int featNum, final String featName, final int min, int max) {
    int loadedProg = Preferences.loadPrefInt(featName, featNum);
    LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setPadding(10, 5, 0, 5);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.setGravity(Gravity.CENTER);
    final TextView textView = new TextView(this);
    textView.setText(
        Html.fromHtml(featName + " -> <font color='" + "#FFFFFF" + "'>" + "[DEFAULT]"));
    textView.setTextColor(TEXT_COLOR);

    textView.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    textView.setTypeface(null, Typeface.BOLD);
    SeekBar seekBar = new SeekBar(this);
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setColor((RBG));
    gradientDrawable.setSize(dp(20), dp(20));
    gradientDrawable.setShape(1);
    gradientDrawable.setStroke(5, Color.RED);
    seekBar.setThumb(gradientDrawable);

    seekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    seekBar.setPadding(25, 10, 35, 10);
    seekBar.setMax(max);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
      seekBar.setMin(min); // setMin for Oreo and above
    seekBar.setProgress((loadedProg == 0) ? min : loadedProg);
    seekBar.getProgressDrawable().setColorFilter(SeekBarProgressColor, PorterDuff.Mode.SRC_ATOP);

    seekBar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
          public void onStartTrackingTouch(SeekBar seekBar) {}

          public void onStopTrackingTouch(SeekBar seekBar) {}

          int l;

          public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            l = i;
            seekBar.setProgress(i < min ? min : i);
            Preferences.changeFeatureInt(featName, featNum, i < min ? min : i);
            if (i == 0) {
              textView.setText(Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
            } else {
              textView.setText(
                  Html.fromHtml(
                      featName + " -> <font color='" + "'>" + "[" + (i < min ? min : i) + "%]"));
            }
            switch (featNum) {
              case -7:
                ICON_ALPHA = (i + 1) / 10.0F;
                if (i == 10)
                  textView.setText(
                      Html.fromHtml(
                          featName + "-> <font color='" + NumberTxtColor + "'>" + "[DEFAULT]"));

                break;
              case 131:
                textView.setText(
                    Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
                if (i >= 1 && i <= 150) {

                  textView.setText(
                      Html.fromHtml(
                          featName + " -> " + "[" + String.format("%.1f", i * 0.1) + "%]"));
                } else if (i >= 150) {
                  textView.setText(Html.fromHtml(featName + " -> [" + (i - 100) + "%]"));
                }

                break;
              case 98:
                textView.setText(
                    Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
                if (i >= 1 && i <= 100) {

                  textView.setText(
                      Html.fromHtml(
                          featName + " -> " + "[" + String.format("%.1f", i * 0.1) + "%]"));
                } else if (i >= 101) {
                  textView.setText(Html.fromHtml(featName + " -> [" + (i - 100) + "%]"));
                }

                break;
              case 107:
                textView.setText(
                    Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
                if (i >= 1 && i <= 100) {

                  textView.setText(
                      Html.fromHtml(
                          featName + " -> " + "[" + String.format("%.1f", i * 0.1) + "%]"));
                } else if (i >= 101) {
                  textView.setText(Html.fromHtml(featName + " -> [" + (i - 100) + "%]"));
                }

                break;
              case 76:
                textView.setText(
                    Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
                if (i >= 1 && i <= 100) {

                  textView.setText(
                      Html.fromHtml(
                          featName + " -> " + "[" + String.format("%.1f", i * 0.1) + "%]"));
                } else if (i >= 101) {
                  textView.setText(Html.fromHtml(featName + " -> [" + (i - 100) + "%]"));
                }

                break;
              case 74:
                textView.setText(
                    Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
                if (i >= 1 && i <= 100) {

                  textView.setText(
                      Html.fromHtml(
                          featName + " -> " + "[" + String.format("%.1f", i * 0.1) + "%]"));
                } else if (i >= 101) {
                  textView.setText(Html.fromHtml(featName + " -> [" + (i - 100) + "%]"));
                }

                break;
              case 75:
                textView.setText(
                    Html.fromHtml(featName + " -> <font color='" + "'>" + "[DEFAULT]"));
                if (i >= 1 && i <= 100) {

                  textView.setText(
                      Html.fromHtml(
                          featName + " -> " + "[" + String.format("%.1f", i * 0.1) + "%]"));
                } else if (i >= 101) {
                  textView.setText(Html.fromHtml(featName + " -> [" + (i - 100) + "%]"));
                }

                break;

              case -9:
                startimage.getLayoutParams().height = (i + 10) * 5;
                startimage.getLayoutParams().width = (i + 10) * 5;
                if (i == 10)
                  textView.setText(
                      Html.fromHtml(
                          featName + "-> <font color='" + NumberTxtColor + "'>" + "[DEFAULT]"));
                textView.setTypeface(
                    Typeface.createFromAsset(getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
                break;
              case -10:
                if (i >= 1) {
                  RotateAnimation rotateAnimation =
                      new RotateAnimation(
                          0,
                          360,
                          Animation.RELATIVE_TO_SELF,
                          0.5f,
                          Animation.RELATIVE_TO_SELF,
                          0.5f);
                  int speed = i > 10 ? ((i - 10) * 1000) : i * 100;
                  rotateAnimation.setDuration(360 * 1000 / speed);
                  rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                  rotateAnimation.setRepeatCount(Animation.INFINITE);
                  startimage.startAnimation(rotateAnimation);
                } else {
                  startimage.clearAnimation();
                }
                break;

              case -400:
                startimage.getLayoutParams().height = ((i + 10) * 2) + 50;
                startimage.getLayoutParams().width = ((i + 10) * 2) + 50;
                if (i == 18) {
                  textView.setText(Html.fromHtml(featName + " -> " + (i + 10) + "% [DEFAULT]"));
                  return;
                } else {
                  textView.setText(Html.fromHtml(featName + " -> " + (i + 10) + "%"));
                  return;
                }
              case -500:
                MENU_HEIGHT = ((i + 20) * 2) + 50;
                ICON_ALPHA = ((float) (i + 1)) / 10.0f;
                if (i == 8) {
                  textView.setText(Html.fromHtml(featName + " -> " + (i + 1) * 10 + "% [DEFAULT]"));
                  return;
                } else {

                  textView.setText(Html.fromHtml(featName + " -> " + (i + 1) * 10 + "%"));
                  return;
                }
            }
          }
        });
    linearLayout.addView(textView);
    linearLayout.addView(seekBar);
    return linearLayout;
  }

  private View Button(final int featNum, final String featName) {
    final Button button = new Button(this);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    layoutParams.setMargins(7, 5, 7, 5);
    button.setLayoutParams(layoutParams);
    button.setTextColor(TEXT_COLOR_2);

    button.setAllCaps(false); // Disable caps to support html
    button.setText(Html.fromHtml(featName));
    button.setBackgroundColor(BTN_COLOR);
    GradientDrawable khadhar = new GradientDrawable();
    khadhar.setColor(RBG);
    khadhar.setStroke(3, Color.CYAN);
    button.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    khadhar.setCornerRadii(new float[] {58, 58, 0, 0, 43, 43, 0, 0});
    button.setBackground(khadhar);
    button.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            switch (featNum) {
              case -4:
                Logcat.Save(getApplicationContext());
                break;
              case -5:
                Logcat.Clear(getApplicationContext());
                break;
              case -6:
                scrollView.removeView(mSettings);
                scrollView.addView(patches);
                break;
              case -100:
                stopChecking = true;
                break;
            }
            Preferences.changeFeatureInt(featName, featNum, 0);
          }
        });

    return button;
  }

  private View ButtonLink(final String featName, final String url) {
    final Button button = new Button(this);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    layoutParams.setMargins(7, 5, 7, 5);
    button.setLayoutParams(layoutParams);
    button.setAllCaps(false); // Disable caps to support html
    button.setTextColor(Color.RED);
    button.setText(Html.fromHtml(featName));
    button.setBackgroundColor(Color.BLACK);
    button.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
            startActivity(intent);
          }
        });
    return button;
  }

  private View ButtonOnOff(final int featNum, String featName, boolean switchedOn) {
    final Button button = new Button(this);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    layoutParams.setMargins(7, 5, 7, 5);
    button.setLayoutParams(layoutParams);
    button.setTextColor(Color.RED);
    button.setAllCaps(false); // Disable caps to support html

    final String finalfeatName = featName.replace("OnOff_", "");
    boolean isOn = Preferences.loadPrefBool(featName, featNum, switchedOn);
    if (isOn) {
      button.setText(Html.fromHtml(finalfeatName + ": ON"));
      button.setBackgroundColor(BtnON);
      isOn = false;
    } else {
      button.setText(Html.fromHtml(finalfeatName + ": OFF"));
      button.setBackgroundColor(BtnOFF);
      isOn = true;
    }
    final boolean finalIsOn = isOn;
    button.setOnClickListener(
        new View.OnClickListener() {
          boolean isOn = finalIsOn;

          public void onClick(View v) {
            Preferences.changeFeatureBool(finalfeatName, featNum, isOn);
            // Log.d(TAG, finalfeatName + " " + featNum + " " + isActive2);
            if (isOn) {
              button.setText(Html.fromHtml(finalfeatName + ": ON"));
              button.setBackgroundColor(BtnON);
              isOn = false;
            } else {
              button.setText(Html.fromHtml(finalfeatName + ": OFF"));
              button.setBackgroundColor(BtnOFF);
              isOn = true;
            }
          }
        });
    return button;
  }

  private View Spinner(final int featNum, final String featName, final String list) {
    Log.d(TAG, "spinner " + featNum + " " + featName + " " + list);
    final List<String> lists = new LinkedList<>(Arrays.asList(list.split(",")));

    LinearLayout mainLayout = new LinearLayout(this);
    mainLayout.setOrientation(LinearLayout.HORIZONTAL);
    LinearLayout.LayoutParams mainLayoutParams =
        new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    mainLayout.setLayoutParams(mainLayoutParams);
    mainLayout.setPadding(10, 0, 10, 0);

    final GradientDrawable buttonDrawable = new GradientDrawable();
    buttonDrawable.setColor(Color.BLACK);
    buttonDrawable.setCornerRadius(7);
    buttonDrawable.setStroke(3, Color.RED);

    final GradientDrawable spinnerBackground = new GradientDrawable();
    spinnerBackground.setColor(Color.WHITE);
    spinnerBackground.setCornerRadius(7);
    spinnerBackground.setStroke(3, Color.RED);

    Button leftButton = new Button(this);
    leftButton.setText("<<");
    leftButton.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    leftButton.setBackground(buttonDrawable);
    leftButton.setTextColor(Color.CYAN);
    leftButton.setLayoutParams(new LinearLayout.LayoutParams(dp(50), dp(45)));
    LinearLayout.LayoutParams leftButtonParams =
        (LinearLayout.LayoutParams) leftButton.getLayoutParams();
    leftButtonParams.setMargins(dp(5), 0, dp(2), dp(5));

    RelativeLayout spinnerContainer = new RelativeLayout(this);
    spinnerContainer.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1));
    spinnerContainer.setBackground(spinnerBackground);

    final Spinner spinner = new Spinner(this, Spinner.MODE_DROPDOWN);
    RelativeLayout.LayoutParams spinnerParams =
        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dp(45));
    spinner.setLayoutParams(spinnerParams);
    spinner.setPadding(dp(10), dp(5), dp(10), dp(5));
    spinner
        .getBackground()
        .setColorFilter(new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR));

    ArrayAdapter<String> aa =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists) {
          @Override
          public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);

            view.setTypeface(
                Typeface.createFromAsset(getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
            ((TextView) view).setTextColor(Color.RED);
            return view;
          }

          @Override
          public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            ((TextView) view).setTextColor(Color.BLACK);
            return view;
          }
        };

    spinner.setAdapter(aa);
    spinner.setSelection(Preferences.loadPrefInt(featName, featNum));

    spinner.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(
              AdapterView<?> parentView, View selectedItemView, int position, long id) {
            Preferences.changeFeatureInt(spinner.getSelectedItem().toString(), featNum, position);
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {}
        });

    spinnerContainer.addView(spinner);

    Button rightButton = new Button(this);
    rightButton.setText(">>");
    rightButton.setBackground(buttonDrawable);
    rightButton.setTextColor(TEXT_COLOR_2);
    rightButton.setLayoutParams(new LinearLayout.LayoutParams(dp(50), dp(45)));
    LinearLayout.LayoutParams rightButtonParams =
        (LinearLayout.LayoutParams) rightButton.getLayoutParams();
    rightButtonParams.setMargins(dp(2), 0, dp(5), dp(5));

    leftButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int currentPosition = spinner.getSelectedItemPosition();
            if (currentPosition > 0) {
              spinner.setSelection(currentPosition - 1);
            }
          }
        });

    rightButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int currentPosition = spinner.getSelectedItemPosition();
            if (currentPosition < lists.size() - 1) {
              spinner.setSelection(currentPosition + 1);
            }
          }
        });

    mainLayout.addView(leftButton);
    mainLayout.addView(spinnerContainer);
    mainLayout.addView(rightButton);

    return mainLayout;
  }

  private View TextField(
      final int feature, final String featName, final boolean numOnly, final int maxValue) {
    final EditTextString edittextstring = new EditTextString();
    final EditTextNum edittextnum = new EditTextNum();
    LinearLayout linearLayout = new LinearLayout(this);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    layoutParams.setMargins(7, 5, 7, 5);

    final Button button = new Button(this);
    if (numOnly) {
      int num = Preferences.loadPrefInt(featName, feature);
      edittextnum.setNum((num == 0) ? 1 : num);
      button.setText(
          Html.fromHtml(
              featName
                  + ": <font color='"
                  + NumberTxtColor
                  + "'>"
                  + ((num == 0) ? 1 : num)
                  + "</font>"));
    } else {
      String string = Preferences.loadPrefString(featName, feature);
      edittextstring.setString((string == "") ? "" : string);
      button.setText(
          Html.fromHtml(featName + ": <font color='" + NumberTxtColor + "'>" + string + "</font>"));
    }
    button.setAllCaps(false);
    button.setLayoutParams(layoutParams);
    button.setBackgroundColor(BTN_COLOR);
    button.setTextColor(TEXT_COLOR_2);
    button.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            final AlertDialog alert = new AlertDialog.Builder(getApplicationContext(), 2).create();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
              Objects.requireNonNull(alert.getWindow())
                  .setType(Build.VERSION.SDK_INT >= 26 ? 2038 : 2002);
            }
            alert.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                  public void onCancel(DialogInterface dialog) {
                    InputMethodManager imm =
                        (InputMethodManager)
                            getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                  }
                });

            // LinearLayout
            LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
            linearLayout1.setPadding(5, 5, 5, 5);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setBackgroundColor(MENU_FEATURE_BG_COLOR);

            // TextView
            final TextView TextViewNote = new TextView(getApplicationContext());
            TextViewNote.setText("Tap OK to apply changes. Tap outside to cancel");
            if (maxValue != 0)
              TextViewNote.setText(
                  "Tap OK to apply changes. Tap outside to cancel\nMax value: " + maxValue);
            TextViewNote.setTextColor(TEXT_COLOR_2);

            // Edit text
            final EditText edittext = new EditText(getApplicationContext());
            edittext.setMaxLines(1);
            edittext.setWidth(convertDipToPixels(300));
            edittext.setTextColor(TEXT_COLOR_2);
            if (numOnly) {
              edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
              edittext.setKeyListener(DigitsKeyListener.getInstance("0123456789-"));
              InputFilter[] FilterArray = new InputFilter[1];
              FilterArray[0] = new InputFilter.LengthFilter(10);
              edittext.setFilters(FilterArray);
            } else {
              edittext.setText(edittextstring.getString());
            }
            edittext.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                  @Override
                  public void onFocusChange(View v, boolean hasFocus) {
                    InputMethodManager imm =
                        (InputMethodManager)
                            getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    if (hasFocus) {
                      imm.toggleSoftInput(
                          InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    } else {
                      imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                  }
                });
            edittext.requestFocus();

            // Button
            Button btndialog = new Button(getApplicationContext());
            btndialog.setBackgroundColor(BTN_COLOR);
            btndialog.setTextColor(TEXT_COLOR_2);
            btndialog.setText("OK");
            btndialog.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    if (numOnly) {
                      int num;
                      try {
                        num =
                            Integer.parseInt(
                                TextUtils.isEmpty(edittext.getText().toString())
                                    ? "0"
                                    : edittext.getText().toString());
                        if (maxValue != 0 && num >= maxValue) num = maxValue;
                      } catch (NumberFormatException ex) {
                        num = 2147483647;
                      }
                      edittextnum.setNum(num);
                      button.setText(
                          Html.fromHtml(featName + ": <font color='#41c300'>" + num + "</font>"));
                      alert.dismiss();
                      Preferences.changeFeatureInt(featName, feature, num);
                    } else {
                      String str = edittext.getText().toString();
                      edittextstring.setString(edittext.getText().toString());
                      button.setText(
                          Html.fromHtml(featName + ": <font color='#41c300'>" + str + "</font>"));
                      alert.dismiss();
                      Preferences.changeFeatureString(featName, feature, str);
                    }
                    edittext.setFocusable(false);
                  }
                });

            linearLayout1.addView(TextViewNote);
            linearLayout1.addView(edittext);
            linearLayout1.addView(btndialog);
            alert.setView(linearLayout1);
            alert.show();
          }
        });

    linearLayout.addView(button);
    return linearLayout;
  }

  private View CheckBox(final int featNum, final String featName, boolean switchedOn) {
    final CheckBox checkBox = new CheckBox(this);
    checkBox.setText(featName);
    checkBox.setTextColor(TEXT_COLOR_2);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      checkBox.setButtonTintList(ColorStateList.valueOf(CheckBoxColor));
    checkBox.setChecked(Preferences.loadPrefBool(featName, featNum, switchedOn));
    checkBox.setOnCheckedChangeListener(
        new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (checkBox.isChecked()) {
              Preferences.changeFeatureBool(featName, featNum, isChecked);
            } else {
              Preferences.changeFeatureBool(featName, featNum, isChecked);
            }
          }
        });
    return checkBox;
  }

  private View RadioButton(
      LinearLayout data, final int featNum, String featName, final String list) {
    final List<String> lists = new LinkedList<>(Arrays.asList(list.split(",")));
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
    layoutParams.setMargins(5, 0, 0, 0);
    HorizontalScrollView scrollh = new HorizontalScrollView(this);
    scrollh.setEnabled(true);
    scrollh.setHorizontalScrollBarEnabled(false);
    LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setOrientation(1);
    linearLayout.setBackgroundColor(Color.TRANSPARENT);
    linearLayout.setLayoutParams(layoutParams);

    final TextView textView = new TextView(this);
    textView.setText(featName + "-->");
    textView.setTextSize(13.5f);
    textView.setTextColor(Color.RED);
    textView.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));

    textView.setPadding(10, 5, 0, 5);
    final RadioGroup radioGroup = new RadioGroup(this);
    radioGroup.setPadding(10, 5, 10, 5);
    radioGroup.setOrientation(LinearLayout.HORIZONTAL);
    linearLayout.addView(textView);
    linearLayout.addView(scrollh);
    //
    scrollh.addView(radioGroup);
    for (int i = 0; i < lists.size(); i++) {
      final RadioButton Radioo = new RadioButton(this);
      final String finalfeatName = featName, radioName = lists.get(i);
      View.OnClickListener first_radio_listener =
          new View.OnClickListener() {
            public void onClick(View v) {
              textView.setText(
                  Html.fromHtml(
                      finalfeatName + ":<font color='" + NumberTxtColor + "'>" + radioName));
              Preferences.changeFeatureInt(finalfeatName, featNum, radioGroup.indexOfChild(Radioo));
            }
          };
      System.out.println(lists.get(i));
      Radioo.setText(lists.get(i));
      Radioo.setTextSize(15);
      Radioo.setTextColor(Color.RED);
      ObjectAnimator colorAnimator =
          ObjectAnimator.ofArgb(
              Radioo, "textColor", Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.MAGENTA);
      colorAnimator.setDuration(2000); // Duraci贸n de la animaci贸n en milisegundos
      colorAnimator.setRepeatCount(
          ObjectAnimator.INFINITE); // Repetir infinitamente (puedes ajustar seg煤n tus necesidades)
      colorAnimator.setRepeatMode(ObjectAnimator.REVERSE); // Invertir la animaci贸n al repetir
      colorAnimator.start();
      Radioo.setTypeface(
          Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        Radioo.setButtonTintList(ColorStateList.valueOf(RadioColor));
      Radioo.setOnClickListener(first_radio_listener);
      radioGroup.addView(Radioo);
    }
    int index = Preferences.loadPrefInt(featName, featNum);
    if (index > 0) { // Preventing it to get an index less than 1. below 1 = null = crash
      textView.setText(
          Html.fromHtml(
              featName + ":<font color='" + NumberTxtColor + "'>" + lists.get(index - 1)));
      ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
    }
    data.addView(linearLayout);
    return linearLayout;
  }

  private void Collapse(LinearLayout linLayout, final String text) {
    LinearLayout.LayoutParams layoutParamsLL =
        new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    layoutParamsLL.setMargins(dp(5), dp(2), dp(5), dp(2));

    LinearLayout collapse = new LinearLayout(this);
    collapse.setLayoutParams(layoutParamsLL);
    collapse.setVerticalGravity(Gravity.CENTER);
    collapse.setOrientation(LinearLayout.VERTICAL);
    final GradientDrawable gdMenuBody2 = new GradientDrawable();
    gdMenuBody2.setColor(Color.parseColor("#00000000"));
    gdMenuBody2.setCornerRadius(dp(10));
    gdMenuBody2.setStroke(dp(2), Color.DKGRAY);
    collapse.setBackgroundDrawable(gdMenuBody2);

    final LinearLayout collapseSub = new LinearLayout(this);
    collapseSub.setVerticalGravity(Gravity.CENTER);
    collapseSub.setPadding(dp(4), dp(4), dp(4), dp(4));
    collapseSub.setOrientation(LinearLayout.VERTICAL);
    collapseSub.setBackgroundColor(Color.TRANSPARENT);
    collapseSub.setVisibility(View.GONE);
    mCollapse = collapseSub;

    final Button textView = new Button(this);
    textView.setText("▽ " + text + " ▽");
    textView.setBackgroundColor(Color.TRANSPARENT);
    textView.setGravity(Gravity.CENTER);
    textView.setTextColor(Color.DKGRAY);
    textView.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    textView.setTextSize(13.5f);
    textView.setSingleLine(true);
    textView.setAllCaps(true);
    textView.setShadowLayer(dp(12), dp(1), dp(1), Color.DKGRAY);
    textView.setPadding(dp(0), dp(3), dp(0), dp(3));
    textView.setOnClickListener(
        new View.OnClickListener() {
          boolean isChecked;

          @Override
          public void onClick(View v) {
            boolean z = !this.isChecked;
            this.isChecked = z;
            if (z) {
              collapseSub.setVisibility(View.VISIBLE);
              textView.setText("︎❌︎ " + text + " ❌︎ ");
              return;
            }
            collapseSub.setVisibility(View.GONE);
            textView.setText("✔︎︎ " + text + "✔︎ ︎");
          }
        });
    collapse.addView(textView);
    collapse.addView(collapseSub);
    linLayout.addView(collapse);
  }

  private View Category(String text) {
    TextView textView = new TextView(this);
    textView.setBackgroundColor(RBG);
    textView.setText(Html.fromHtml(text));
    GradientDrawable KILLSHOT = new GradientDrawable();
    KILLSHOT.setColor(RBG);
    textView.setTypeface(
        Typeface.createFromAsset(this.getAssets(), "LuyinorFont/LuyinorModz/Ehromtj13.ttf"));
    KILLSHOT.setStroke(4, Color.DKGRAY);
    KILLSHOT.setCornerRadii(new float[] {33, 33, 33, 33, 33, 33, 33, 33});
    textView.setBackground(KILLSHOT);
    textView.setGravity(Gravity.CENTER);
    textView.setTextColor(TEXT_COLOR_2);
    textView.setTypeface(null, Typeface.BOLD);
    textView.setPadding(3, 10, 3, 10);
    return textView;
  }

  private View RichTextView(String text) {
    TextView textView = new TextView(this);
    textView.setText(Html.fromHtml(text));
    textView.setTextColor(TEXT_COLOR_2);
    textView.setPadding(10, 5, 10, 5);
    return textView;
  }

  private void Shap(
      View view,
      int color,
      float corner,
      int shape,
      int strokeWidth,
      int strokeColor,
      int width,
      int height) {
    GradientDrawable gd = new GradientDrawable();
    gd.setColor(color);
    gd.setShape(shape);
    gd.setSize(width, height);
    gd.setCornerRadius(corner);
    gd.setStroke(strokeWidth, strokeColor);
    view.setBackgroundDrawable(gd);
  }

  private void Color(View view, int color, float corner, int strokeWidth, int strokeColor) {
    GradientDrawable gd = new GradientDrawable();
    gd.setColor(color);
    gd.setCornerRadius(corner);
    gd.setStroke(strokeWidth, strokeColor);
    view.setBackgroundDrawable(gd);
  }

  private void AddColor(
      View view,
      int color,
      int strokeWidth,
      int dashWidth,
      int dashGap,
      int strokeColor,
      int r1,
      int r2,
      int r3,
      int r4,
      int r5,
      int r6,
      int r7,
      int r8) {
    GradientDrawable gd = new GradientDrawable();
    gd.setColor(color);
    gd.setCornerRadii(new float[] {r1, r2, r3, r4, r5, r6, r7, r8});
    gd.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
    view.setBackgroundDrawable(gd);
  }

  private String rgbToHsl(int r, int g, int b) {
    final float rf = r / 255f;
    final float gf = g / 255f;
    final float bf = b / 255f;
    final float max = Math.max(rf, Math.max(gf, bf));
    final float min = Math.min(rf, Math.min(gf, bf));
    final float deltaMaxMin = max - min;
    float h, s;
    float l = (max + min) / 2f;
    if (max == min) {
      // Monochromatic
      h = s = 0f;
    } else {
      if (max == rf) {
        h = ((gf - bf) / deltaMaxMin) % 6f;
      } else if (max == gf) {
        h = ((bf - rf) / deltaMaxMin) + 2f;
      } else {
        h = ((rf - gf) / deltaMaxMin) + 4f;
      }
      s = deltaMaxMin / (1f - Math.abs(2f * l - 1f));
    }
    h = (h * 60f) % 360f;
    s = s * 100;
    l = l * 100;

    NumberFormat formH = NumberFormat.getNumberInstance();
    formH.setMinimumFractionDigits(0);
    formH.setMaximumFractionDigits(0);
    String formattedH = formH.format(h);

    NumberFormat formS = NumberFormat.getNumberInstance();
    formS.setMinimumFractionDigits(0);
    formS.setMaximumFractionDigits(0);
    String formattedS = formS.format(s);

    NumberFormat formL = NumberFormat.getNumberInstance();
    formL.setMinimumFractionDigits(0);
    formL.setMaximumFractionDigits(0);
    String formattedL = formL.format(l);
    return "" + formattedH + ", " + formattedS + "%, " + formattedL + "%";
  }

  private View RichWebView(String text) {
    WebView wView = new WebView(this);
    wView.loadData(text, "text/html", "utf-8");
    wView.setBackgroundColor(0x00000000); // Transparent
    wView.setPadding(0, 5, 0, 5);
    return wView;
  }

  // Override our Start Command so the Service doesnt try to recreate itself when the App is closed
  public int onStartCommand(Intent intent, int i, int i2) {
    return Service.START_NOT_STICKY;
  }

  private boolean isViewCollapsed() {
    return rootFrame == null || mCollapsed.getVisibility() == View.VISIBLE;
  }

  // For our image a little converter
  private int convertDipToPixels(int i) {
    return (int) ((((float) i) * getResources().getDisplayMetrics().density) + 0.5f);
  }

  private int dp(int i) {
    return (int) TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
  }

  // Check if we are still in the game. If now our menu and menu button will dissapear
  private boolean isNotInGame() {
    RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo();
    ActivityManager.getMyMemoryState(runningAppProcessInfo);
    return runningAppProcessInfo.importance != 100;
  }

  // Destroy our View
  public void onDestroy() {
    super.onDestroy();
    if (rootFrame != null) {
      mWindowManager.removeView(rootFrame);
    }
  }

  // Same as above so it wont crash in the background and therefore use alot of Battery life
  public void onTaskRemoved(Intent intent) {
    super.onTaskRemoved(intent);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stopSelf();
  }

  private void Thread() {
    if (rootFrame == null) {
      return;
    }
    if (isNotInGame()) {
      rootFrame.setVisibility(View.VISIBLE);
    } else {
      rootFrame.setVisibility(View.VISIBLE);
    }
  }

  private class EditTextString {
    private String text;

    public void setString(String s) {
      text = s;
    }

    public String getString() {
      return text;
    }
  }

  private class EditTextNum {
    private int val;

    public void setNum(int i) {
      val = i;
    }

    public int getNum() {
      return val;
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
