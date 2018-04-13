/*
 * Copyright (c) 1998-2018 Caucho Technology -- all rights reserved
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

package com.caucho.bytecode;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents a method ref in the constant pool.
 */
public class MethodHandleConstant extends ConstantPoolEntry {
  private int _referenceKind;
  private int _referenceIndex;

  /**
   * Creates a new field ref constant.
   */
  MethodHandleConstant(ConstantPool pool, int index,
                        int referenceKind, int referenceIndex)
  {
    super(pool, index);

    _referenceKind = referenceKind;
    _referenceIndex = referenceIndex;
  }

  /**
   * Returns the class index.
   */
  public int getBaseMethodIndex()
  {
    return _referenceKind;
  }

  /**
   * Sets the class index.
   */
  /*
  public void setClassIndex(int index)
  {
    _baseMethodIndex = index;
  }
  */

  /**
   * Returns the class name
   */
  /*
  public String getClassName()
  {
    return getConstantPool().getClass(_baseMethodIndex).getName();
  }
  */

  /**
   * Returns the method name
   */
  public String getName()
  {
    return getConstantPool().getNameAndType(_referenceIndex).getName();
  }

  /**
   * Returns the method type
   */
  public String getType()
  {
    return getConstantPool().getNameAndType(_referenceIndex).getType();
  }

  /**
   * Sets the method name and type
   */
  public void setNameAndType(String name, String type)
  {
    _referenceIndex = getConstantPool().addNameAndType(name, type).getIndex();
  }

  /**
   * Writes the contents of the pool entry.
   */
  void write(ByteCodeWriter out)
    throws IOException
  {
    out.write(ConstantPool.CP_METHOD_HANDLE);
    out.write(_referenceKind);
    out.writeShort(_referenceIndex);
  }

  /**
   * Exports to the target pool.
   */
  public int export(ConstantPool target)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }

  public String toString()
  {
    return getClass().getSimpleName() + "[" + getName() + "(" + getType() + ")]";
  }
}
