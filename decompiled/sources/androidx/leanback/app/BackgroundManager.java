package androidx.leanback.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.leanback.R;
import androidx.leanback.widget.BackgroundHelper;
import java.lang.ref.WeakReference;
/* loaded from: classes.dex */
public final class BackgroundManager {
    private static final int CHANGE_BG_DELAY_MS = 500;
    static final boolean DEBUG = false;
    private static final int FADE_DURATION = 500;
    private static final String FRAGMENT_TAG = "androidx.leanback.app.BackgroundManager";
    static final int FULL_ALPHA = 255;
    static final String TAG = "BackgroundManager";
    private final Interpolator mAccelerateInterpolator;
    private final Animator.AnimatorListener mAnimationListener;
    private final ValueAnimator.AnimatorUpdateListener mAnimationUpdateListener;
    final ValueAnimator mAnimator;
    boolean mAttached;
    private boolean mAutoReleaseOnStop = true;
    int mBackgroundColor;
    Drawable mBackgroundDrawable;
    private View mBgView;
    ChangeBackgroundRunnable mChangeRunnable;
    private boolean mChangeRunnablePending;
    Activity mContext;
    private final Interpolator mDecelerateInterpolator;
    private BackgroundFragment mFragmentState;
    Handler mHandler;
    private int mHeightPx;
    int mImageInWrapperIndex;
    int mImageOutWrapperIndex;
    private long mLastSetTime;
    TranslucentLayerDrawable mLayerDrawable;
    private BackgroundContinuityService mService;
    private int mThemeDrawableResourceId;
    private int mWidthPx;

    @Deprecated
    public Drawable getDimLayer() {
        return null;
    }

    @Deprecated
    public void setDimLayer(Drawable drawable) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class BitmapDrawable extends Drawable {
        boolean mMutated;
        ConstantState mState;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public static final class ConstantState extends Drawable.ConstantState {
            final Bitmap mBitmap;
            final Matrix mMatrix;
            final Paint mPaint;

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }

            ConstantState(Bitmap bitmap, Matrix matrix) {
                Paint paint = new Paint();
                this.mPaint = paint;
                this.mBitmap = bitmap;
                this.mMatrix = matrix == null ? new Matrix() : matrix;
                paint.setFilterBitmap(true);
            }

            ConstantState(ConstantState constantState) {
                Paint paint = new Paint();
                this.mPaint = paint;
                this.mBitmap = constantState.mBitmap;
                this.mMatrix = constantState.mMatrix != null ? new Matrix(constantState.mMatrix) : new Matrix();
                if (constantState.mPaint.getAlpha() != 255) {
                    paint.setAlpha(constantState.mPaint.getAlpha());
                }
                if (constantState.mPaint.getColorFilter() != null) {
                    paint.setColorFilter(constantState.mPaint.getColorFilter());
                }
                paint.setFilterBitmap(true);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return new BitmapDrawable(this);
            }
        }

        BitmapDrawable(Resources resources, Bitmap bitmap) {
            this(resources, bitmap, null);
        }

        BitmapDrawable(Resources resources, Bitmap bitmap, Matrix matrix) {
            this.mState = new ConstantState(bitmap, matrix);
        }

        BitmapDrawable(ConstantState constantState) {
            this.mState = constantState;
        }

        Bitmap getBitmap() {
            return this.mState.mBitmap;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.mState.mBitmap == null) {
                return;
            }
            if (this.mState.mPaint.getAlpha() < 255 && this.mState.mPaint.getColorFilter() != null) {
                throw new IllegalStateException("Can't draw with translucent alpha and color filter");
            }
            canvas.drawBitmap(this.mState.mBitmap, this.mState.mMatrix, this.mState.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            mutate();
            if (this.mState.mPaint.getAlpha() != i) {
                this.mState.mPaint.setAlpha(i);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            mutate();
            this.mState.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public ColorFilter getColorFilter() {
            return this.mState.mPaint.getColorFilter();
        }

        @Override // android.graphics.drawable.Drawable
        public ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable mutate() {
            if (!this.mMutated) {
                this.mMutated = true;
                this.mState = new ConstantState(this.mState);
            }
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class DrawableWrapper {
        int mAlpha;
        final Drawable mDrawable;

        public DrawableWrapper(Drawable drawable) {
            this.mAlpha = 255;
            this.mDrawable = drawable;
        }

        public DrawableWrapper(DrawableWrapper drawableWrapper, Drawable drawable) {
            this.mAlpha = 255;
            this.mDrawable = drawable;
            this.mAlpha = drawableWrapper.mAlpha;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public void setColor(int i) {
            ((ColorDrawable) this.mDrawable).setColor(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class TranslucentLayerDrawable extends LayerDrawable {
        int mAlpha;
        WeakReference<BackgroundManager> mManagerWeakReference;
        boolean mSuspendInvalidation;
        DrawableWrapper[] mWrapper;

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        TranslucentLayerDrawable(BackgroundManager backgroundManager, Drawable[] drawableArr) {
            super(drawableArr);
            this.mAlpha = 255;
            this.mManagerWeakReference = new WeakReference<>(backgroundManager);
            int length = drawableArr.length;
            this.mWrapper = new DrawableWrapper[length];
            for (int i = 0; i < length; i++) {
                this.mWrapper[i] = new DrawableWrapper(drawableArr[i]);
            }
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            if (this.mAlpha != i) {
                this.mAlpha = i;
                invalidateSelf();
                BackgroundManager backgroundManager = this.mManagerWeakReference.get();
                if (backgroundManager != null) {
                    backgroundManager.postChangeRunnable();
                }
            }
        }

        void setWrapperAlpha(int i, int i2) {
            DrawableWrapper drawableWrapper = this.mWrapper[i];
            if (drawableWrapper != null) {
                drawableWrapper.mAlpha = i2;
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public int getAlpha() {
            return this.mAlpha;
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public Drawable mutate() {
            Drawable mutate = super.mutate();
            int numberOfLayers = getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                DrawableWrapper[] drawableWrapperArr = this.mWrapper;
                DrawableWrapper drawableWrapper = drawableWrapperArr[i];
                if (drawableWrapper != null) {
                    drawableWrapperArr[i] = new DrawableWrapper(drawableWrapper, getDrawable(i));
                }
            }
            return mutate;
        }

        @Override // android.graphics.drawable.LayerDrawable
        public boolean setDrawableByLayerId(int i, Drawable drawable) {
            return updateDrawable(i, drawable) != null;
        }

        public DrawableWrapper updateDrawable(int i, Drawable drawable) {
            super.setDrawableByLayerId(i, drawable);
            for (int i2 = 0; i2 < getNumberOfLayers(); i2++) {
                if (getId(i2) == i) {
                    this.mWrapper[i2] = new DrawableWrapper(drawable);
                    invalidateSelf();
                    return this.mWrapper[i2];
                }
            }
            return null;
        }

        public void clearDrawable(int i, Context context) {
            for (int i2 = 0; i2 < getNumberOfLayers(); i2++) {
                if (getId(i2) == i) {
                    this.mWrapper[i2] = null;
                    if (getDrawable(i2) instanceof EmptyDrawable) {
                        return;
                    }
                    super.setDrawableByLayerId(i, BackgroundManager.createEmptyDrawable(context));
                    return;
                }
            }
        }

        public int findWrapperIndexById(int i) {
            for (int i2 = 0; i2 < getNumberOfLayers(); i2++) {
                if (getId(i2) == i) {
                    return i2;
                }
            }
            return -1;
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(Drawable drawable) {
            if (this.mSuspendInvalidation) {
                return;
            }
            super.invalidateDrawable(drawable);
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Drawable drawable;
            int i;
            int i2;
            int i3 = 0;
            while (true) {
                DrawableWrapper[] drawableWrapperArr = this.mWrapper;
                if (i3 >= drawableWrapperArr.length) {
                    return;
                }
                DrawableWrapper drawableWrapper = drawableWrapperArr[i3];
                if (drawableWrapper != null && (drawable = drawableWrapper.getDrawable()) != null) {
                    int alpha = DrawableCompat.getAlpha(drawable);
                    int i4 = this.mAlpha;
                    if (i4 < 255) {
                        i = i4 * alpha;
                        i2 = 1;
                    } else {
                        i = alpha;
                        i2 = 0;
                    }
                    if (this.mWrapper[i3].mAlpha < 255) {
                        i *= this.mWrapper[i3].mAlpha;
                        i2++;
                    }
                    if (i2 == 0) {
                        drawable.draw(canvas);
                    } else {
                        if (i2 == 1) {
                            i /= 255;
                        } else if (i2 == 2) {
                            i /= 65025;
                        }
                        try {
                            this.mSuspendInvalidation = true;
                            drawable.setAlpha(i);
                            drawable.draw(canvas);
                            drawable.setAlpha(alpha);
                        } finally {
                            this.mSuspendInvalidation = false;
                        }
                    }
                }
                i3++;
            }
        }
    }

    TranslucentLayerDrawable createTranslucentLayerDrawable(LayerDrawable layerDrawable) {
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        Drawable[] drawableArr = new Drawable[numberOfLayers];
        for (int i = 0; i < numberOfLayers; i++) {
            drawableArr[i] = layerDrawable.getDrawable(i);
        }
        TranslucentLayerDrawable translucentLayerDrawable = new TranslucentLayerDrawable(this, drawableArr);
        for (int i2 = 0; i2 < numberOfLayers; i2++) {
            translucentLayerDrawable.setId(i2, layerDrawable.getId(i2));
        }
        return translucentLayerDrawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class BackgroundContinuityService {
        private static final boolean DEBUG = false;
        private static final String TAG = "BackgroundContinuity";
        private static BackgroundContinuityService sService = new BackgroundContinuityService();
        private int mColor;
        private int mCount;
        private Drawable mDrawable;
        private int mLastThemeDrawableId;
        private WeakReference<Drawable.ConstantState> mLastThemeDrawableState;

        private BackgroundContinuityService() {
            reset();
        }

        private void reset() {
            this.mColor = 0;
            this.mDrawable = null;
        }

        public static BackgroundContinuityService getInstance() {
            BackgroundContinuityService backgroundContinuityService = sService;
            backgroundContinuityService.mCount++;
            return backgroundContinuityService;
        }

        public void unref() {
            int i = this.mCount;
            if (i <= 0) {
                throw new IllegalStateException("Can't unref, count " + this.mCount);
            }
            int i2 = i - 1;
            this.mCount = i2;
            if (i2 == 0) {
                reset();
            }
        }

        public int getColor() {
            return this.mColor;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public void setColor(int i) {
            this.mColor = i;
            this.mDrawable = null;
        }

        public void setDrawable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public Drawable getThemeDrawable(Context context, int i) {
            Drawable.ConstantState constantState;
            WeakReference<Drawable.ConstantState> weakReference = this.mLastThemeDrawableState;
            Drawable newDrawable = (weakReference == null || this.mLastThemeDrawableId != i || (constantState = weakReference.get()) == null) ? null : constantState.newDrawable();
            if (newDrawable == null) {
                Drawable drawable = ContextCompat.getDrawable(context, i);
                this.mLastThemeDrawableState = new WeakReference<>(drawable.getConstantState());
                this.mLastThemeDrawableId = i;
                return drawable;
            }
            return newDrawable;
        }
    }

    Drawable getDefaultDrawable() {
        if (this.mBackgroundColor != 0) {
            return new ColorDrawable(this.mBackgroundColor);
        }
        return getThemeDrawable();
    }

    private Drawable getThemeDrawable() {
        int i = this.mThemeDrawableResourceId;
        Drawable themeDrawable = i != -1 ? this.mService.getThemeDrawable(this.mContext, i) : null;
        return themeDrawable == null ? createEmptyDrawable(this.mContext) : themeDrawable;
    }

    public static BackgroundManager getInstance(Activity activity) {
        BackgroundManager backgroundManager;
        BackgroundFragment backgroundFragment = (BackgroundFragment) activity.getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        return (backgroundFragment == null || (backgroundManager = backgroundFragment.getBackgroundManager()) == null) ? new BackgroundManager(activity) : backgroundManager;
    }

    private BackgroundManager(Activity activity) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: androidx.leanback.app.BackgroundManager.1
            final Runnable mRunnable = new Runnable() { // from class: androidx.leanback.app.BackgroundManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    BackgroundManager.this.postChangeRunnable();
                }
            };

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (BackgroundManager.this.mLayerDrawable != null) {
                    BackgroundManager.this.mLayerDrawable.clearDrawable(R.id.background_imageout, BackgroundManager.this.mContext);
                }
                BackgroundManager.this.mHandler.post(this.mRunnable);
            }
        };
        this.mAnimationListener = animatorListener;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.leanback.app.BackgroundManager.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                if (BackgroundManager.this.mImageInWrapperIndex != -1) {
                    BackgroundManager.this.mLayerDrawable.setWrapperAlpha(BackgroundManager.this.mImageInWrapperIndex, intValue);
                }
            }
        };
        this.mAnimationUpdateListener = animatorUpdateListener;
        this.mContext = activity;
        this.mService = BackgroundContinuityService.getInstance();
        this.mHeightPx = this.mContext.getResources().getDisplayMetrics().heightPixels;
        this.mWidthPx = this.mContext.getResources().getDisplayMetrics().widthPixels;
        this.mHandler = new Handler();
        FastOutLinearInInterpolator fastOutLinearInInterpolator = new FastOutLinearInInterpolator();
        this.mAccelerateInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17432581);
        this.mDecelerateInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17432582);
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        this.mAnimator = ofInt;
        ofInt.addListener(animatorListener);
        ofInt.addUpdateListener(animatorUpdateListener);
        ofInt.setInterpolator(fastOutLinearInInterpolator);
        TypedArray obtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
        this.mThemeDrawableResourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        createFragment(activity);
    }

    private void createFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        String str = FRAGMENT_TAG;
        BackgroundFragment backgroundFragment = (BackgroundFragment) fragmentManager.findFragmentByTag(str);
        if (backgroundFragment == null) {
            backgroundFragment = new BackgroundFragment();
            activity.getFragmentManager().beginTransaction().add(backgroundFragment, str).commit();
        } else if (backgroundFragment.getBackgroundManager() != null) {
            throw new IllegalStateException("Created duplicated BackgroundManager for same activity, please use getInstance() instead");
        }
        backgroundFragment.setBackgroundManager(this);
        this.mFragmentState = backgroundFragment;
    }

    DrawableWrapper getImageInWrapper() {
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable == null) {
            return null;
        }
        return translucentLayerDrawable.mWrapper[this.mImageInWrapperIndex];
    }

    DrawableWrapper getImageOutWrapper() {
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable == null) {
            return null;
        }
        return translucentLayerDrawable.mWrapper[this.mImageOutWrapperIndex];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onActivityStart() {
        updateImmediate();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onStop() {
        if (isAutoReleaseOnStop()) {
            release();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onResume() {
        postChangeRunnable();
    }

    private void syncWithService() {
        int color = this.mService.getColor();
        Drawable drawable = this.mService.getDrawable();
        this.mBackgroundColor = color;
        this.mBackgroundDrawable = drawable == null ? null : drawable.getConstantState().newDrawable().mutate();
        updateImmediate();
    }

    public void attach(Window window) {
        attachToViewInternal(window.getDecorView());
    }

    public void setThemeDrawableResourceId(int i) {
        this.mThemeDrawableResourceId = i;
    }

    public void attachToView(View view) {
        attachToViewInternal(view);
        this.mContext.getWindow().getDecorView().setBackground(Build.VERSION.SDK_INT >= 26 ? null : new ColorDrawable(0));
    }

    void attachToViewInternal(View view) {
        if (this.mAttached) {
            throw new IllegalStateException("Already attached to " + this.mBgView);
        }
        this.mBgView = view;
        this.mAttached = true;
        syncWithService();
    }

    public boolean isAttached() {
        return this.mAttached;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void detach() {
        release();
        this.mBgView = null;
        this.mAttached = false;
        BackgroundContinuityService backgroundContinuityService = this.mService;
        if (backgroundContinuityService != null) {
            backgroundContinuityService.unref();
            this.mService = null;
        }
    }

    public void release() {
        ChangeBackgroundRunnable changeBackgroundRunnable = this.mChangeRunnable;
        if (changeBackgroundRunnable != null) {
            this.mHandler.removeCallbacks(changeBackgroundRunnable);
            this.mChangeRunnable = null;
        }
        if (this.mAnimator.isStarted()) {
            this.mAnimator.cancel();
        }
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable != null) {
            translucentLayerDrawable.clearDrawable(R.id.background_imagein, this.mContext);
            this.mLayerDrawable.clearDrawable(R.id.background_imageout, this.mContext);
            this.mLayerDrawable = null;
        }
        this.mBackgroundDrawable = null;
    }

    @Deprecated
    public Drawable getDefaultDimLayer() {
        return ContextCompat.getDrawable(this.mContext, R.color.lb_background_protection);
    }

    void postChangeRunnable() {
        if (this.mChangeRunnable == null || !this.mChangeRunnablePending || this.mAnimator.isStarted() || !this.mFragmentState.isResumed() || this.mLayerDrawable.getAlpha() < 255) {
            return;
        }
        long runnableDelay = getRunnableDelay();
        this.mLastSetTime = System.currentTimeMillis();
        this.mHandler.postDelayed(this.mChangeRunnable, runnableDelay);
        this.mChangeRunnablePending = false;
    }

    private void lazyInit() {
        if (this.mLayerDrawable != null) {
            return;
        }
        TranslucentLayerDrawable createTranslucentLayerDrawable = createTranslucentLayerDrawable((LayerDrawable) ContextCompat.getDrawable(this.mContext, R.drawable.lb_background).mutate());
        this.mLayerDrawable = createTranslucentLayerDrawable;
        this.mImageInWrapperIndex = createTranslucentLayerDrawable.findWrapperIndexById(R.id.background_imagein);
        this.mImageOutWrapperIndex = this.mLayerDrawable.findWrapperIndexById(R.id.background_imageout);
        BackgroundHelper.setBackgroundPreservingAlpha(this.mBgView, this.mLayerDrawable);
    }

    private void updateImmediate() {
        if (this.mAttached) {
            lazyInit();
            if (this.mBackgroundDrawable == null) {
                this.mLayerDrawable.updateDrawable(R.id.background_imagein, getDefaultDrawable());
            } else {
                this.mLayerDrawable.updateDrawable(R.id.background_imagein, this.mBackgroundDrawable);
            }
            this.mLayerDrawable.clearDrawable(R.id.background_imageout, this.mContext);
        }
    }

    public void setColor(int i) {
        this.mService.setColor(i);
        this.mBackgroundColor = i;
        this.mBackgroundDrawable = null;
        if (this.mLayerDrawable == null) {
            return;
        }
        setDrawableInternal(getDefaultDrawable());
    }

    public void setDrawable(Drawable drawable) {
        this.mService.setDrawable(drawable);
        this.mBackgroundDrawable = drawable;
        if (this.mLayerDrawable == null) {
            return;
        }
        if (drawable == null) {
            setDrawableInternal(getDefaultDrawable());
        } else {
            setDrawableInternal(drawable);
        }
    }

    public void clearDrawable() {
        setDrawable(null);
    }

    private void setDrawableInternal(Drawable drawable) {
        if (!this.mAttached) {
            throw new IllegalStateException("Must attach before setting background drawable");
        }
        ChangeBackgroundRunnable changeBackgroundRunnable = this.mChangeRunnable;
        if (changeBackgroundRunnable != null) {
            if (sameDrawable(drawable, changeBackgroundRunnable.mDrawable)) {
                return;
            }
            this.mHandler.removeCallbacks(this.mChangeRunnable);
            this.mChangeRunnable = null;
        }
        this.mChangeRunnable = new ChangeBackgroundRunnable(drawable);
        this.mChangeRunnablePending = true;
        postChangeRunnable();
    }

    private long getRunnableDelay() {
        return Math.max(0L, (this.mLastSetTime + 500) - System.currentTimeMillis());
    }

    public void setBitmap(Bitmap bitmap) {
        Matrix matrix = null;
        if (bitmap == null) {
            setDrawable(null);
        } else if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
        } else {
            if (bitmap.getWidth() != this.mWidthPx || bitmap.getHeight() != this.mHeightPx) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int i = this.mHeightPx;
                int i2 = width * i;
                int i3 = this.mWidthPx;
                float f = i2 > i3 * height ? i / height : i3 / width;
                int max = Math.max(0, (width - Math.min((int) (i3 / f), width)) / 2);
                Matrix matrix2 = new Matrix();
                matrix2.setScale(f, f);
                matrix2.preTranslate(-max, 0.0f);
                matrix = matrix2;
            }
            setDrawable(new BitmapDrawable(this.mContext.getResources(), bitmap, matrix));
        }
    }

    public void setAutoReleaseOnStop(boolean z) {
        this.mAutoReleaseOnStop = z;
    }

    public boolean isAutoReleaseOnStop() {
        return this.mAutoReleaseOnStop;
    }

    public final int getColor() {
        return this.mBackgroundColor;
    }

    public Drawable getDrawable() {
        return this.mBackgroundDrawable;
    }

    boolean sameDrawable(Drawable drawable, Drawable drawable2) {
        if (drawable != null && drawable2 != null) {
            if (drawable == drawable2) {
                return true;
            }
            if ((drawable instanceof BitmapDrawable) && (drawable2 instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap().sameAs(((BitmapDrawable) drawable2).getBitmap())) {
                return true;
            }
            if ((drawable instanceof ColorDrawable) && (drawable2 instanceof ColorDrawable) && ((ColorDrawable) drawable).getColor() == ((ColorDrawable) drawable2).getColor()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class ChangeBackgroundRunnable implements Runnable {
        final Drawable mDrawable;

        ChangeBackgroundRunnable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        @Override // java.lang.Runnable
        public void run() {
            runTask();
            BackgroundManager.this.mChangeRunnable = null;
        }

        private void runTask() {
            if (BackgroundManager.this.mLayerDrawable == null) {
                return;
            }
            DrawableWrapper imageInWrapper = BackgroundManager.this.getImageInWrapper();
            if (imageInWrapper != null) {
                if (BackgroundManager.this.sameDrawable(this.mDrawable, imageInWrapper.getDrawable())) {
                    return;
                }
                BackgroundManager.this.mLayerDrawable.clearDrawable(R.id.background_imagein, BackgroundManager.this.mContext);
                BackgroundManager.this.mLayerDrawable.updateDrawable(R.id.background_imageout, imageInWrapper.getDrawable());
            }
            applyBackgroundChanges();
        }

        void applyBackgroundChanges() {
            if (BackgroundManager.this.mAttached) {
                if (BackgroundManager.this.getImageInWrapper() == null && this.mDrawable != null) {
                    BackgroundManager.this.mLayerDrawable.updateDrawable(R.id.background_imagein, this.mDrawable);
                    BackgroundManager.this.mLayerDrawable.setWrapperAlpha(BackgroundManager.this.mImageInWrapperIndex, 0);
                }
                BackgroundManager.this.mAnimator.setDuration(500L);
                BackgroundManager.this.mAnimator.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class EmptyDrawable extends BitmapDrawable {
        EmptyDrawable(Resources resources) {
            super(resources, null);
        }
    }

    static Drawable createEmptyDrawable(Context context) {
        return new EmptyDrawable(context.getResources());
    }
}
