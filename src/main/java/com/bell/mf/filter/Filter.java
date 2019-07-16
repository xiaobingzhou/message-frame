package com.bell.mf.filter;

import com.bell.mf.handler.MessageFrameRequest;

public interface Filter {
	 public void doFilter(MessageFrameRequest request, FilterChain filterChain);
}
