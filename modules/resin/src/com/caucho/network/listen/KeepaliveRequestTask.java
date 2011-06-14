/*
 * Copyright (c) 1998-2011 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is software; you can redistribute it and/or modify
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

package com.caucho.network.listen;

import java.io.IOException;

import com.caucho.inject.Module;

/**
 * A protocol-independent TcpConnection.  TcpConnection controls the
 * TCP Socket and provides buffered streams.
 *
 * <p>Each TcpConnection has its own thread.
 */
@Module
class KeepaliveRequestTask extends ConnectionReadTask {
  KeepaliveRequestTask(TcpSocketLink socketLink)
  {
    super(socketLink);
  }

  @Override
  public void run()
  {
    SocketLinkThreadLauncher launcher = getLauncher();
    
    launcher.onChildThreadResume();

    try {
      super.run();
    } finally {
      launcher.onChildThreadEnd();
    }
  }

  @Override
  public RequestState doTask()
  throws IOException
  {
    TcpSocketLink socketLink = getSocketLink();
    
    boolean isKeepalive = true;
    RequestState result = socketLink.handleRequests(isKeepalive);

    switch (result) {
    case KEEPALIVE_SELECT:
    case ASYNC:
      return result;
      
    case DUPLEX:
      return socketLink.doDuplex();
      
    case EXIT:
      socketLink.close();
      return result;
      
    case REQUEST_COMPLETE:
      // acceptTask significantly faster than finishing
      socketLink.close();
      return socketLink.doAccept();
      
    default:
      throw new IllegalStateException(String.valueOf(result));
    }
  }
}
