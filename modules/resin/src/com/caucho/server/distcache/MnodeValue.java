/*
 * Copyright (c) 1998-2011 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.server.distcache;

import com.caucho.util.HashKey;
import com.caucho.util.Hex;

/**
 * An entry in the cache map
 */
@SuppressWarnings("serial")
public class MnodeValue implements java.io.Serializable {
  private final byte[] _valueHash;
  private final long _valueLength;
  
  private final byte[] _cacheHash;
  
  private final long _flags;
  
  private final long _accessedExpireTimeout;
  private final long _modifiedExpireTimeout;
  
  private final long _version;
  
  public MnodeValue(byte []valueHash,
                    long valueLength,
                    long version,
                    byte []cacheHash,
                    long flags,
                    long accessedExpireTimeout,
                    long modifiedExpireTimeout)
  {
    _valueHash = valueHash;
    _valueLength = valueLength;
    
    _cacheHash = cacheHash;
    
    _flags = flags;
    
    _accessedExpireTimeout = accessedExpireTimeout;
    _modifiedExpireTimeout = modifiedExpireTimeout;
   
    _version = version;
  }
  
  public MnodeValue(byte []valueHash, 
                    long valueLength,
                    long version)
  {
    this(valueHash, valueLength, version, null, 0, 0, 0);
  }
  
  public MnodeValue(MnodeValue mnodeValue)
  {
    _valueHash = mnodeValue._valueHash;
    _valueLength = mnodeValue._valueLength;
    
    _cacheHash = mnodeValue._cacheHash;
    
    _flags = mnodeValue._flags;
    
    _modifiedExpireTimeout = mnodeValue._modifiedExpireTimeout;
    _accessedExpireTimeout = mnodeValue._accessedExpireTimeout;
    
    _version = mnodeValue._version;
  }
  
  public MnodeValue(byte []valueHash,
                    long valueLength,
                    long version,
                    MnodeValue oldValue)
  {
    _valueHash = valueHash;
    _valueLength = valueLength;
    
    _version = version;
    
    if (oldValue != null) {
      _cacheHash = oldValue._cacheHash;
      _flags = oldValue._flags;
      _modifiedExpireTimeout = oldValue._modifiedExpireTimeout;
      _accessedExpireTimeout = oldValue._accessedExpireTimeout;
    }
    else {
      _cacheHash = null;
      _flags = 0;
      _modifiedExpireTimeout = -1;
      _accessedExpireTimeout = -1;
    }
  }

  public MnodeValue(byte []valueHash,
                    long valueLength,
                    long version,
                    CacheConfig config)
  {
    _valueHash = valueHash;
    _valueLength = valueLength;
    
    _version = version;
    
    _cacheHash = HashKey.getHash(config.getCacheKey());
    
    _flags = config.getFlags();
    
    _modifiedExpireTimeout = config.getModifiedExpireTimeout();
    _accessedExpireTimeout = config.getAccessedExpireTimeout();
  }
  
  public MnodeValue(HashKey valueHash,
                    long valueLength,
                    long version,
                    CacheConfig config)
  {
    this(HashKey.getHash(valueHash),
         valueLength,
         version,
         config);
  }
  
  /*
  public MnodeValue(MnodeEntry mnodeValue)
  {
    _valueHash = mnodeValue.getValueHash();
    _valueLength = mnodeValue.getValueLength();
  }
  */
  
  public final byte []getValueHash()
  {
    return _valueHash;
  }
  
  public final long getValueLength()
  {
    return _valueLength;
  }
  
  public final long getVersion()
  {
    return _version;
  }
  
  public final byte []getCacheHash()
  {
    return _cacheHash;
  }
  
  public final long getFlags()
  {
    return _flags;
  }
  
  public final int getUserFlags()
  {
    return (int) (getFlags() >> 32);
  }
  
  public final long getModifiedExpireTimeout()
  {
    return _modifiedExpireTimeout;
  }
  
  public final long getAccessedExpireTimeout()
  {
    return _accessedExpireTimeout;
  }

  @Override
  public String toString()
  {
    return (getClass().getSimpleName()
            + "["
            + ",value=" + Hex.toHex(getValueHash(), 0, 4)
            + ",flags=" + Long.toHexString(getFlags())
            + ",version=" + Long.toHexString(getVersion())
            + "]");
  }
}
