package com.lsh.lib.android.zxing;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.lsh.lib.android.R;
import com.lsh.lib.android.zxing.camera.CameraManager;
import com.lsh.lib.android.zxing.widgets.ViewfinderView;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;


/**
 * Author:liush
 * Date:2016/9/3
 */
public class ZxingFragment extends Fragment implements SurfaceHolder.Callback {
    View main;
    private CameraManager cameraManager;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;
    private IntentSource source;

    private CaptureActivityHandler handler;
    private boolean isprepare = false;

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (isprepare) {
//            if (isVisibleToUser) {
//                onResume();
//            } else {
//                onPause();
//            }
//        }
//
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.fragment_zxing, container, false);

        return main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(getActivity());
        beepManager = new BeepManager(getActivity());
        ambientLightManager = new AmbientLightManager(getContext());
        isprepare = true;
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        cameraManager = new CameraManager(getContext());
        viewfinderView = (ViewfinderView) main.findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        SurfaceView surfaceView = (SurfaceView) main.findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();
        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());

//        if (prefs.getBoolean(Constants.KEY_DISABLE_AUTO_ORIENTATION,
//                true)) {
//            setRequestedOrientation(getCurrentOrientation());
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) main.findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
                               int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (holder == null) {
            Log.e("CaptureActivity",
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        hasSurface = false;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.e("CaptureActivity",
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }

        try {
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    decodeHints, characterSet, cameraManager);
        }

    }

    private int getCurrentOrientation() {
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_90:
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            default:
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        }
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(final Result rawResult, Bitmap barcode,
                             float scaleFactor) {
        inactivityTimer.onActivity();
        boolean fromLiveScan = barcode != null;
        if (fromLiveScan) {
            // delete 2017.17.29 dzt
//             historyManager.addHistoryItem(rawResult, resultHandler);
            // Then not from history, so beep/vibrate and we have an image to
            // draw on
            beepManager.playBeepSoundAndVibrate();
            // drawResultPoints(barcode, scaleFactor, rawResult);
        }
        handlerResult(rawResult.getText());
        Log.e("ZxingFragment",
                "解析结果" + rawResult.getText());
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    /**
     * 处理获取的数据
     *
     * @param qrStr
     */
    public void handlerResult(String qrStr) {
        handler.sendEmptyMessageDelayed(R.id.restart_preview, 5 * 1000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("onHiddenChanged", "" + hidden);
        if (hidden) {
            onPause();
        } else {
            onResume();

        }
    }
}
