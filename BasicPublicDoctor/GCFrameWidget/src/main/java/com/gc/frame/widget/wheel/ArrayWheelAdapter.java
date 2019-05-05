/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.gc.frame.widget.wheel;

import java.util.List;

import android.content.Context;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter extends AbstractWheelTextAdapter {
    private String[] items;
    
	public ArrayWheelAdapter(Context context, List<String> list) {
		this(context, list.toArray(new String[list.size()]));
	}

    public ArrayWheelAdapter(Context context, String[] items) {
        super(context);
        this.items = items;
        final float scale = context.getResources().getDisplayMetrics().density;
        //        setTextSize(sp2px(12, scale));
    }
    
    @Override
    public int getItemsCount() {
        if (items != null) {
			return items.length;
		} else {
			return 0;
		}
    }
    
    @Override
    protected CharSequence getItemText(int index) {
        return items[index];
    }
    
    
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }
    
    
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

	@Override
	public String getItem(int index) {
		return items[index];
	}

	@Override
	public int getMaximumLength() {
		return 0;
	}
}
