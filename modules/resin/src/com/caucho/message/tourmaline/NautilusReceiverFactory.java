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

package com.caucho.message.tourmaline;

import com.caucho.message.MessageReceiver;
import com.caucho.message.SettleMode;
import com.caucho.message.common.AbstractMessageReceiverFactory;

/**
 * factory to create local receivers.
 */
class NautilusReceiverFactory extends AbstractMessageReceiverFactory {
  private final NautilusClientConnection _conn;

  public NautilusReceiverFactory(NautilusClientConnection conn)
  {
    _conn = conn;
  }

  @Override
  public NautilusReceiverFactory setAddress(String address)
  {
    super.setAddress(address);
    
    return this;
  }

  @Override
  public NautilusReceiverFactory setSettleMode(SettleMode settleMode)
  {
    super.setSettleMode(settleMode);
    
    return this;
  }

  @Override
  public NautilusReceiverFactory setPrefetch(int prefetch)
  {
    super.setPrefetch(prefetch);

    return this;
  }
  
  NautilusClientConnection getConnection()
  {
    return _conn;
  }

  @Override
  public MessageReceiver<?> build()
  {
    NautilusClientReceiver<?> receiver = new NautilusClientReceiver(this);
    
    receiver.onBuild();
    
    return receiver;
  }
}
