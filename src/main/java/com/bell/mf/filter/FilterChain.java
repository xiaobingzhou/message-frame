package com.bell.mf.filter;

import java.util.ArrayList;
import java.util.List;

import com.bell.mf.handler.MessageFrameRequest;

public class FilterChain implements Filter {

	private List<Filter> list = new ArrayList<Filter>();

	public FilterChain addfilter(Filter filter) {
		list.add(filter);
		return this;
	}

	int index = 0;

	@Override
	public void doFilter(MessageFrameRequest req, FilterChain fc) {
		if (index == list.size())
			return;
		Filter f = list.get(index);
		index++;
		// 依次执行下一个过滤器，直到整个过滤器链执行完
		f.doFilter(req, fc);
	}

}
