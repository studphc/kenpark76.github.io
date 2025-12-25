package androidx.leanback.app;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;
import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.BaseOnItemViewSelectedListener;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.DetailsParallax;
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import androidx.leanback.widget.ItemAlignmentFacet;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;
import java.lang.ref.WeakReference;
/* loaded from: classes.dex */
public class DetailsSupportFragment extends BaseSupportFragment {
    static final boolean DEBUG = false;
    static final String TAG = "DetailsSupportFragment";
    ObjectAdapter mAdapter;
    Drawable mBackgroundDrawable;
    View mBackgroundView;
    int mContainerListAlignTop;
    DetailsSupportFragmentBackgroundController mDetailsBackgroundController;
    DetailsParallax mDetailsParallax;
    BaseOnItemViewSelectedListener mExternalOnItemViewSelectedListener;
    BaseOnItemViewClickedListener mOnItemViewClickedListener;
    BrowseFrameLayout mRootView;
    RowsSupportFragment mRowsSupportFragment;
    Object mSceneAfterEntranceTransition;
    Fragment mVideoSupportFragment;
    WaitEnterTransitionTimeout mWaitEnterTransitionTimeout;
    final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State("STATE_SET_ENTRANCE_START_STATE") { // from class: androidx.leanback.app.DetailsSupportFragment.1
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsSupportFragment.this.mRowsSupportFragment.setEntranceTransitionState(false);
        }
    };
    final StateMachine.State STATE_ENTER_TRANSITION_INIT = new StateMachine.State("STATE_ENTER_TRANSIITON_INIT");
    final StateMachine.State STATE_SWITCH_TO_VIDEO_IN_ON_CREATE = new StateMachine.State("STATE_SWITCH_TO_VIDEO_IN_ON_CREATE", false, false) { // from class: androidx.leanback.app.DetailsSupportFragment.2
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsSupportFragment.this.switchToVideoBeforeVideoSupportFragmentCreated();
        }
    };
    final StateMachine.State STATE_ENTER_TRANSITION_CANCEL = new StateMachine.State("STATE_ENTER_TRANSITION_CANCEL", false, false) { // from class: androidx.leanback.app.DetailsSupportFragment.3
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            if (DetailsSupportFragment.this.mWaitEnterTransitionTimeout != null) {
                DetailsSupportFragment.this.mWaitEnterTransitionTimeout.mRef.clear();
            }
            if (DetailsSupportFragment.this.getActivity() != null) {
                Window window = DetailsSupportFragment.this.getActivity().getWindow();
                Object returnTransition = TransitionHelper.getReturnTransition(window);
                Object sharedElementReturnTransition = TransitionHelper.getSharedElementReturnTransition(window);
                TransitionHelper.setEnterTransition(window, (Object) null);
                TransitionHelper.setSharedElementEnterTransition(window, (Object) null);
                TransitionHelper.setReturnTransition(window, returnTransition);
                TransitionHelper.setSharedElementReturnTransition(window, sharedElementReturnTransition);
            }
        }
    };
    final StateMachine.State STATE_ENTER_TRANSITION_COMPLETE = new StateMachine.State("STATE_ENTER_TRANSIITON_COMPLETE", true, false);
    final StateMachine.State STATE_ENTER_TRANSITION_ADDLISTENER = new StateMachine.State("STATE_ENTER_TRANSITION_PENDING") { // from class: androidx.leanback.app.DetailsSupportFragment.4
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            TransitionHelper.addTransitionListener(TransitionHelper.getEnterTransition(DetailsSupportFragment.this.getActivity().getWindow()), DetailsSupportFragment.this.mEnterTransitionListener);
        }
    };
    final StateMachine.State STATE_ENTER_TRANSITION_PENDING = new StateMachine.State("STATE_ENTER_TRANSITION_PENDING") { // from class: androidx.leanback.app.DetailsSupportFragment.5
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            if (DetailsSupportFragment.this.mWaitEnterTransitionTimeout == null) {
                new WaitEnterTransitionTimeout(DetailsSupportFragment.this);
            }
        }
    };
    final StateMachine.State STATE_ON_SAFE_START = new StateMachine.State("STATE_ON_SAFE_START") { // from class: androidx.leanback.app.DetailsSupportFragment.6
        @Override // androidx.leanback.util.StateMachine.State
        public void run() {
            DetailsSupportFragment.this.onSafeStart();
        }
    };
    final StateMachine.Event EVT_ONSTART = new StateMachine.Event("onStart");
    final StateMachine.Event EVT_NO_ENTER_TRANSITION = new StateMachine.Event("EVT_NO_ENTER_TRANSITION");
    final StateMachine.Event EVT_DETAILS_ROW_LOADED = new StateMachine.Event("onFirstRowLoaded");
    final StateMachine.Event EVT_ENTER_TRANSIITON_DONE = new StateMachine.Event("onEnterTransitionDone");
    final StateMachine.Event EVT_SWITCH_TO_VIDEO = new StateMachine.Event("switchToVideo");
    TransitionListener mEnterTransitionListener = new TransitionListener() { // from class: androidx.leanback.app.DetailsSupportFragment.7
        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionStart(Object obj) {
            if (DetailsSupportFragment.this.mWaitEnterTransitionTimeout != null) {
                DetailsSupportFragment.this.mWaitEnterTransitionTimeout.mRef.clear();
            }
        }

        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionCancel(Object obj) {
            DetailsSupportFragment.this.mStateMachine.fireEvent(DetailsSupportFragment.this.EVT_ENTER_TRANSIITON_DONE);
        }

        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionEnd(Object obj) {
            DetailsSupportFragment.this.mStateMachine.fireEvent(DetailsSupportFragment.this.EVT_ENTER_TRANSIITON_DONE);
        }
    };
    TransitionListener mReturnTransitionListener = new TransitionListener() { // from class: androidx.leanback.app.DetailsSupportFragment.8
        @Override // androidx.leanback.transition.TransitionListener
        public void onTransitionStart(Object obj) {
            DetailsSupportFragment.this.onReturnTransitionStart();
        }
    };
    boolean mPendingFocusOnVideo = false;
    final SetSelectionRunnable mSetSelectionRunnable = new SetSelectionRunnable();
    final BaseOnItemViewSelectedListener<Object> mOnItemViewSelectedListener = new BaseOnItemViewSelectedListener<Object>() { // from class: androidx.leanback.app.DetailsSupportFragment.9
        @Override // androidx.leanback.widget.BaseOnItemViewSelectedListener
        public void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, Object obj2) {
            DetailsSupportFragment.this.onRowSelected(DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().getSelectedPosition(), DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().getSelectedSubPosition());
            if (DetailsSupportFragment.this.mExternalOnItemViewSelectedListener != null) {
                DetailsSupportFragment.this.mExternalOnItemViewSelectedListener.onItemSelected(viewHolder, obj, viewHolder2, obj2);
            }
        }
    };

    void switchToVideoBeforeVideoSupportFragmentCreated() {
        this.mDetailsBackgroundController.switchToVideoBeforeCreate();
        showTitle(false);
        this.mPendingFocusOnVideo = true;
        slideOutGridView();
    }

    /* loaded from: classes.dex */
    static class WaitEnterTransitionTimeout implements Runnable {
        static final long WAIT_ENTERTRANSITION_START = 200;
        final WeakReference<DetailsSupportFragment> mRef;

        WaitEnterTransitionTimeout(DetailsSupportFragment detailsSupportFragment) {
            this.mRef = new WeakReference<>(detailsSupportFragment);
            detailsSupportFragment.getView().postDelayed(this, WAIT_ENTERTRANSITION_START);
        }

        @Override // java.lang.Runnable
        public void run() {
            DetailsSupportFragment detailsSupportFragment = this.mRef.get();
            if (detailsSupportFragment != null) {
                detailsSupportFragment.mStateMachine.fireEvent(detailsSupportFragment.EVT_ENTER_TRANSIITON_DONE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.leanback.app.BaseSupportFragment
    public void createStateMachineStates() {
        super.createStateMachineStates();
        this.mStateMachine.addState(this.STATE_SET_ENTRANCE_START_STATE);
        this.mStateMachine.addState(this.STATE_ON_SAFE_START);
        this.mStateMachine.addState(this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_INIT);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_ADDLISTENER);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_CANCEL);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_PENDING);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_COMPLETE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.leanback.app.BaseSupportFragment
    public void createStateMachineTransitions() {
        super.createStateMachineTransitions();
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ENTER_TRANSITION_INIT, this.EVT_ON_CREATE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_COMPLETE, this.COND_TRANSITION_NOT_SUPPORTED);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_NO_ENTER_TRANSITION);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_CANCEL, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_CANCEL, this.STATE_ENTER_TRANSITION_COMPLETE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_ADDLISTENER, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_ADDLISTENER, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_ENTER_TRANSIITON_DONE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_ADDLISTENER, this.STATE_ENTER_TRANSITION_PENDING, this.EVT_DETAILS_ROW_LOADED);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_PENDING, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_ENTER_TRANSIITON_DONE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_COMPLETE, this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.STATE_ENTRANCE_COMPLETE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_COMPLETE, this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_SET_ENTRANCE_START_STATE, this.EVT_ONSTART);
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ON_SAFE_START, this.EVT_ONSTART);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_COMPLETE, this.STATE_ON_SAFE_START);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_COMPLETE, this.STATE_ON_SAFE_START);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SetSelectionRunnable implements Runnable {
        int mPosition;
        boolean mSmooth = true;

        SetSelectionRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (DetailsSupportFragment.this.mRowsSupportFragment == null) {
                return;
            }
            DetailsSupportFragment.this.mRowsSupportFragment.setSelectedPosition(this.mPosition, this.mSmooth);
        }
    }

    public void setAdapter(ObjectAdapter objectAdapter) {
        this.mAdapter = objectAdapter;
        Presenter[] presenters = objectAdapter.getPresenterSelector().getPresenters();
        if (presenters != null) {
            for (Presenter presenter : presenters) {
                setupPresenter(presenter);
            }
        } else {
            Log.e(TAG, "PresenterSelector.getPresenters() not implemented");
        }
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment != null) {
            rowsSupportFragment.setAdapter(objectAdapter);
        }
    }

    public ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener baseOnItemViewSelectedListener) {
        this.mExternalOnItemViewSelectedListener = baseOnItemViewSelectedListener;
    }

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener baseOnItemViewClickedListener) {
        if (this.mOnItemViewClickedListener != baseOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = baseOnItemViewClickedListener;
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (rowsSupportFragment != null) {
                rowsSupportFragment.setOnItemViewClickedListener(baseOnItemViewClickedListener);
            }
        }
    }

    public BaseOnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    @Override // androidx.leanback.app.BaseSupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContainerListAlignTop = getResources().getDimensionPixelSize(R.dimen.lb_details_rows_align_top);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (TransitionHelper.getEnterTransition(activity.getWindow()) == null) {
                this.mStateMachine.fireEvent(this.EVT_NO_ENTER_TRANSITION);
            }
            Object returnTransition = TransitionHelper.getReturnTransition(activity.getWindow());
            if (returnTransition != null) {
                TransitionHelper.addTransitionListener(returnTransition, this.mReturnTransitionListener);
                return;
            }
            return;
        }
        this.mStateMachine.fireEvent(this.EVT_NO_ENTER_TRANSITION);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BrowseFrameLayout browseFrameLayout = (BrowseFrameLayout) layoutInflater.inflate(R.layout.lb_details_fragment, viewGroup, false);
        this.mRootView = browseFrameLayout;
        View findViewById = browseFrameLayout.findViewById(R.id.details_background_view);
        this.mBackgroundView = findViewById;
        if (findViewById != null) {
            findViewById.setBackground(this.mBackgroundDrawable);
        }
        RowsSupportFragment rowsSupportFragment = (RowsSupportFragment) getChildFragmentManager().findFragmentById(R.id.details_rows_dock);
        this.mRowsSupportFragment = rowsSupportFragment;
        if (rowsSupportFragment == null) {
            this.mRowsSupportFragment = new RowsSupportFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.details_rows_dock, this.mRowsSupportFragment).commit();
        }
        installTitleView(layoutInflater, this.mRootView, bundle);
        this.mRowsSupportFragment.setAdapter(this.mAdapter);
        this.mRowsSupportFragment.setOnItemViewSelectedListener(this.mOnItemViewSelectedListener);
        this.mRowsSupportFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(this.mRootView, new Runnable() { // from class: androidx.leanback.app.DetailsSupportFragment.10
            @Override // java.lang.Runnable
            public void run() {
                DetailsSupportFragment.this.mRowsSupportFragment.setEntranceTransitionState(true);
            }
        });
        setupDpadNavigation();
        this.mRowsSupportFragment.setExternalAdapterListener(new ItemBridgeAdapter.AdapterListener() { // from class: androidx.leanback.app.DetailsSupportFragment.11
            @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
            public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
                if (DetailsSupportFragment.this.mDetailsParallax == null || !(viewHolder.getViewHolder() instanceof FullWidthDetailsOverviewRowPresenter.ViewHolder)) {
                    return;
                }
                ((FullWidthDetailsOverviewRowPresenter.ViewHolder) viewHolder.getViewHolder()).getOverviewView().setTag(R.id.lb_parallax_source, DetailsSupportFragment.this.mDetailsParallax);
            }
        });
        return this.mRootView;
    }

    @Deprecated
    protected View inflateTitle(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onInflateTitleView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.leanback.app.BrandedSupportFragment
    public View onInflateTitleView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return inflateTitle(layoutInflater, viewGroup, bundle);
    }

    void setVerticalGridViewLayout(VerticalGridView verticalGridView) {
        verticalGridView.setItemAlignmentOffset(-this.mContainerListAlignTop);
        verticalGridView.setItemAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignmentOffset(0);
        verticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignment(0);
    }

    protected void setupPresenter(Presenter presenter) {
        if (presenter instanceof FullWidthDetailsOverviewRowPresenter) {
            setupDetailsOverviewRowPresenter((FullWidthDetailsOverviewRowPresenter) presenter);
        }
    }

    protected void setupDetailsOverviewRowPresenter(FullWidthDetailsOverviewRowPresenter fullWidthDetailsOverviewRowPresenter) {
        ItemAlignmentFacet itemAlignmentFacet = new ItemAlignmentFacet();
        ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef = new ItemAlignmentFacet.ItemAlignmentDef();
        itemAlignmentDef.setItemAlignmentViewId(R.id.details_frame);
        itemAlignmentDef.setItemAlignmentOffset(-getResources().getDimensionPixelSize(R.dimen.lb_details_v2_align_pos_for_actions));
        itemAlignmentDef.setItemAlignmentOffsetPercent(0.0f);
        ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef2 = new ItemAlignmentFacet.ItemAlignmentDef();
        itemAlignmentDef2.setItemAlignmentViewId(R.id.details_frame);
        itemAlignmentDef2.setItemAlignmentFocusViewId(R.id.details_overview_description);
        itemAlignmentDef2.setItemAlignmentOffset(-getResources().getDimensionPixelSize(R.dimen.lb_details_v2_align_pos_for_description));
        itemAlignmentDef2.setItemAlignmentOffsetPercent(0.0f);
        itemAlignmentFacet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{itemAlignmentDef, itemAlignmentDef2});
        fullWidthDetailsOverviewRowPresenter.setFacet(ItemAlignmentFacet.class, itemAlignmentFacet);
    }

    VerticalGridView getVerticalGridView() {
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment == null) {
            return null;
        }
        return rowsSupportFragment.getVerticalGridView();
    }

    public RowsSupportFragment getRowsSupportFragment() {
        return this.mRowsSupportFragment;
    }

    private void setupChildFragmentLayout() {
        setVerticalGridViewLayout(this.mRowsSupportFragment.getVerticalGridView());
    }

    public void setSelectedPosition(int i) {
        setSelectedPosition(i, true);
    }

    public void setSelectedPosition(int i, boolean z) {
        this.mSetSelectionRunnable.mPosition = i;
        this.mSetSelectionRunnable.mSmooth = z;
        if (getView() == null || getView().getHandler() == null) {
            return;
        }
        getView().getHandler().post(this.mSetSelectionRunnable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void switchToVideo() {
        Fragment fragment = this.mVideoSupportFragment;
        if (fragment != null && fragment.getView() != null) {
            this.mVideoSupportFragment.getView().requestFocus();
        } else {
            this.mStateMachine.fireEvent(this.EVT_SWITCH_TO_VIDEO);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void switchToRows() {
        this.mPendingFocusOnVideo = false;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView == null || verticalGridView.getChildCount() <= 0) {
            return;
        }
        verticalGridView.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Fragment findOrCreateVideoSupportFragment() {
        Fragment fragment = this.mVideoSupportFragment;
        if (fragment != null) {
            return fragment;
        }
        Fragment findFragmentById = getChildFragmentManager().findFragmentById(R.id.video_surface_container);
        if (findFragmentById == null && this.mDetailsBackgroundController != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            int i = R.id.video_surface_container;
            Fragment onCreateVideoSupportFragment = this.mDetailsBackgroundController.onCreateVideoSupportFragment();
            beginTransaction.add(i, onCreateVideoSupportFragment);
            beginTransaction.commit();
            if (this.mPendingFocusOnVideo) {
                getView().post(new Runnable() { // from class: androidx.leanback.app.DetailsSupportFragment.12
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DetailsSupportFragment.this.getView() != null) {
                            DetailsSupportFragment.this.switchToVideo();
                        }
                        DetailsSupportFragment.this.mPendingFocusOnVideo = false;
                    }
                });
            }
            findFragmentById = onCreateVideoSupportFragment;
        }
        this.mVideoSupportFragment = findFragmentById;
        return findFragmentById;
    }

    void onRowSelected(int i, int i2) {
        ObjectAdapter adapter = getAdapter();
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment != null && rowsSupportFragment.getView() != null && this.mRowsSupportFragment.getView().hasFocus() && !this.mPendingFocusOnVideo && (adapter == null || adapter.size() == 0 || (getVerticalGridView().getSelectedPosition() == 0 && getVerticalGridView().getSelectedSubPosition() == 0))) {
            showTitle(true);
        } else {
            showTitle(false);
        }
        if (adapter == null || adapter.size() <= i) {
            return;
        }
        VerticalGridView verticalGridView = getVerticalGridView();
        int childCount = verticalGridView.getChildCount();
        if (childCount > 0) {
            this.mStateMachine.fireEvent(this.EVT_DETAILS_ROW_LOADED);
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i3));
            RowPresenter rowPresenter = (RowPresenter) viewHolder.getPresenter();
            onSetRowStatus(rowPresenter, rowPresenter.getRowViewHolder(viewHolder.getViewHolder()), viewHolder.getAdapterPosition(), i, i2);
        }
    }

    void onSafeStart() {
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController != null) {
            detailsSupportFragmentBackgroundController.onStart();
        }
    }

    void onReturnTransitionStart() {
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController == null || detailsSupportFragmentBackgroundController.disableVideoParallax() || this.mVideoSupportFragment == null) {
            return;
        }
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.remove(this.mVideoSupportFragment);
        beginTransaction.commit();
        this.mVideoSupportFragment = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController != null) {
            detailsSupportFragmentBackgroundController.onStop();
        }
        super.onStop();
    }

    protected void onSetRowStatus(RowPresenter rowPresenter, RowPresenter.ViewHolder viewHolder, int i, int i2, int i3) {
        if (rowPresenter instanceof FullWidthDetailsOverviewRowPresenter) {
            onSetDetailsOverviewRowStatus((FullWidthDetailsOverviewRowPresenter) rowPresenter, (FullWidthDetailsOverviewRowPresenter.ViewHolder) viewHolder, i, i2, i3);
        }
    }

    protected void onSetDetailsOverviewRowStatus(FullWidthDetailsOverviewRowPresenter fullWidthDetailsOverviewRowPresenter, FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder, int i, int i2, int i3) {
        if (i2 > i) {
            fullWidthDetailsOverviewRowPresenter.setState(viewHolder, 0);
        } else if (i2 == i && i3 == 1) {
            fullWidthDetailsOverviewRowPresenter.setState(viewHolder, 0);
        } else if (i2 == i && i3 == 0) {
            fullWidthDetailsOverviewRowPresenter.setState(viewHolder, 1);
        } else {
            fullWidthDetailsOverviewRowPresenter.setState(viewHolder, 2);
        }
    }

    @Override // androidx.leanback.app.BrandedSupportFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        setupChildFragmentLayout();
        this.mStateMachine.fireEvent(this.EVT_ONSTART);
        DetailsParallax detailsParallax = this.mDetailsParallax;
        if (detailsParallax != null) {
            detailsParallax.setRecyclerView(this.mRowsSupportFragment.getVerticalGridView());
        }
        if (this.mPendingFocusOnVideo) {
            slideOutGridView();
        } else if (getView().hasFocus()) {
        } else {
            this.mRowsSupportFragment.getVerticalGridView().requestFocus();
        }
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    protected Object createEntranceTransition() {
        return TransitionHelper.loadTransition(getContext(), R.transition.lb_details_enter_transition);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    protected void runEntranceTransition(Object obj) {
        TransitionHelper.runTransition(this.mSceneAfterEntranceTransition, obj);
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    protected void onEntranceTransitionEnd() {
        this.mRowsSupportFragment.onTransitionEnd();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    protected void onEntranceTransitionPrepare() {
        this.mRowsSupportFragment.onTransitionPrepare();
    }

    @Override // androidx.leanback.app.BaseSupportFragment
    protected void onEntranceTransitionStart() {
        this.mRowsSupportFragment.onTransitionStart();
    }

    public DetailsParallax getParallax() {
        if (this.mDetailsParallax == null) {
            this.mDetailsParallax = new DetailsParallax();
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (rowsSupportFragment != null && rowsSupportFragment.getView() != null) {
                this.mDetailsParallax.setRecyclerView(this.mRowsSupportFragment.getVerticalGridView());
            }
        }
        return this.mDetailsParallax;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundDrawable(Drawable drawable) {
        View view = this.mBackgroundView;
        if (view != null) {
            view.setBackground(drawable);
        }
        this.mBackgroundDrawable = drawable;
    }

    void setupDpadNavigation() {
        this.mRootView.setOnChildFocusListener(new BrowseFrameLayout.OnChildFocusListener() { // from class: androidx.leanback.app.DetailsSupportFragment.13
            @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
            public boolean onRequestFocusInDescendants(int i, Rect rect) {
                return false;
            }

            @Override // androidx.leanback.widget.BrowseFrameLayout.OnChildFocusListener
            public void onRequestChildFocus(View view, View view2) {
                if (view != DetailsSupportFragment.this.mRootView.getFocusedChild()) {
                    if (view.getId() == R.id.details_fragment_root) {
                        if (DetailsSupportFragment.this.mPendingFocusOnVideo) {
                            return;
                        }
                        DetailsSupportFragment.this.slideInGridView();
                        DetailsSupportFragment.this.showTitle(true);
                    } else if (view.getId() == R.id.video_surface_container) {
                        DetailsSupportFragment.this.slideOutGridView();
                        DetailsSupportFragment.this.showTitle(false);
                    } else {
                        DetailsSupportFragment.this.showTitle(true);
                    }
                }
            }
        });
        this.mRootView.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() { // from class: androidx.leanback.app.DetailsSupportFragment.14
            @Override // androidx.leanback.widget.BrowseFrameLayout.OnFocusSearchListener
            public View onFocusSearch(View view, int i) {
                if (DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView() == null || !DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().hasFocus()) {
                    return (DetailsSupportFragment.this.getTitleView() == null || !DetailsSupportFragment.this.getTitleView().hasFocus() || i != 130 || DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView() == null) ? view : DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView();
                } else if (i == 33) {
                    if (DetailsSupportFragment.this.mDetailsBackgroundController == null || !DetailsSupportFragment.this.mDetailsBackgroundController.canNavigateToVideoSupportFragment() || DetailsSupportFragment.this.mVideoSupportFragment == null || DetailsSupportFragment.this.mVideoSupportFragment.getView() == null) {
                        return (DetailsSupportFragment.this.getTitleView() == null || !DetailsSupportFragment.this.getTitleView().hasFocusable()) ? view : DetailsSupportFragment.this.getTitleView();
                    }
                    return DetailsSupportFragment.this.mVideoSupportFragment.getView();
                } else {
                    return view;
                }
            }
        });
        this.mRootView.setOnDispatchKeyListener(new View.OnKeyListener() { // from class: androidx.leanback.app.DetailsSupportFragment.15
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (DetailsSupportFragment.this.mVideoSupportFragment == null || DetailsSupportFragment.this.mVideoSupportFragment.getView() == null || !DetailsSupportFragment.this.mVideoSupportFragment.getView().hasFocus()) {
                    return false;
                }
                if ((i == 4 || i == 111) && DetailsSupportFragment.this.getVerticalGridView().getChildCount() > 0) {
                    DetailsSupportFragment.this.getVerticalGridView().requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    void slideOutGridView() {
        if (getVerticalGridView() != null) {
            getVerticalGridView().animateOut();
        }
    }

    void slideInGridView() {
        if (getVerticalGridView() != null) {
            getVerticalGridView().animateIn();
        }
    }
}
