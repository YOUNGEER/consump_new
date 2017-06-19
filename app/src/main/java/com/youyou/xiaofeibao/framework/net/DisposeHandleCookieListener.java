package com.youyou.xiaofeibao.framework.net;

import com.youyou.xiaofeibao.net.DisposeDataListener;

import java.util.ArrayList;

public interface DisposeHandleCookieListener extends DisposeDataListener
{
	public void onCookie(ArrayList<String> cookieStrLists);
}
