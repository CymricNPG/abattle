package net.npg.abattle.client.asset.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

@SuppressWarnings("all")
public class MyFileHandleResolver implements FileHandleResolver {
  @Override
  public FileHandle resolve(final String fileName) {
    FileHandle _xblockexpression = null;
    {
      final FileHandle classpath = Gdx.files.classpath(fileName);
      boolean _exists = classpath.exists();
      if (_exists) {
        return classpath;
      }
      _xblockexpression = Gdx.files.internal(fileName);
    }
    return _xblockexpression;
  }
}
