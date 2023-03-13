package com.test.usersapi.util;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import java.io.IOException;
import java.util.List;

public class CustomHelpers {

  public static Helper<List<?>> listSizeHelper = new Helper<List<?>>() {
    @Override
    public Object apply(List<?> context, Options options) throws IOException {
      return context.size();
    }
  };
}
