package com.youyou.xiaofeibao.framework.net;

import com.youyou.xiaofeibao.net.DisposeDataListener;

/**
 * @author vision
 * @function 监听下载进度
 */
public interface DisposeDownloadListener extends DisposeDataListener {
	public void onProgress(int progrss);
}
