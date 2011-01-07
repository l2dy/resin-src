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
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.amber.idgen;

import com.caucho.amber.manager.AmberConnection;
import com.caucho.util.L10N;
import com.caucho.util.Log;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Generator table.
 */
abstract public class IdGenerator {
  private static final L10N L = new L10N(IdGenerator.class);
  private static final Logger log = Log.open(IdGenerator.class);
  
  private long _next;
  private int _remaining;

  private int _groupSize = 50;
  
  /**
   * Gets the group size.
   */
  public int getGroupSize()
  {
    return _groupSize;
  }
  
  /**
   * Sets the group size.
   */
  public void setGroupSize(int groupSize)
  {
    _groupSize = groupSize;
  }

  /**
   * Allocates the next id.
   */
  public long allocate(AmberConnection aConn)
    throws SQLException
  {
    synchronized (this) {
      if (_remaining <= 0) {
        _next = allocateGroup(aConn);
        _remaining += getGroupSize();
      }
      
      long value = _next;
      _next++;
      _remaining--;

      return value;
    }
  }

  /**
   * Allocates the next group of ids.
   */
  abstract public long allocateGroup(AmberConnection aConn)
    throws SQLException;

  /**
   * Starts the generator
   */
  public void start()
  {
  }
}
