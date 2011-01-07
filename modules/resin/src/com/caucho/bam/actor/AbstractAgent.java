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

package com.caucho.bam.actor;

import com.caucho.bam.broker.Broker;
import com.caucho.bam.mailbox.Mailbox;

/**
 * A BAM Actor sends and receives messages as the core class in a
 * service-oriented architecture.
 *
 * <h2>Core API</h2>
 *
 * Each actor has a unique JID, which is the address for messages sent to
 * the actor.  JIDs look like email addresses: harry@caucho.com
 * or harry@caucho.com/browser13.
 *
 * {@link com.caucho.bam.stream.ActorStream} is the key customizable interface
 * for an agent developer.  Developers will implement callbacks for each
 * packet type the agent understands.
 *
 * Most developers will extend from {@link com.caucho.bam.actor.SimpleActor}
 * instead of implementing Actor directly.  SimpleActor adds an
 * annotation-based message dispatching system to simplify Actor development.
 */
public class AbstractAgent implements Agent
{
  private String _jid;
  private Mailbox _mailbox;
  private Broker _broker;
  
  protected AbstractAgent()
  {
  }
  
  public AbstractAgent(String jid,
                       Mailbox mailbox,
                       Broker broker)
  {
    _jid = jid;
    _mailbox = mailbox;
    _broker = broker;
  }
  
  /**
   * Returns the actor's jid, so the {@link com.caucho.bam.broker.Broker} can
   * deliver messages to this actor.
   */
  @Override
  public String getJid()
  {
    return _jid;
  }

  /**
   * The stream to send messages to the actor.
   */
  @Override
  public Mailbox getMailbox()
  {
    return _mailbox;
  }

  /**
   * Returns the actor's broker.
   */
  @Override
  public Broker getBroker()
  {
    return _broker;
  }

  @Override
  public String toString()
  {
    return getClass().getSimpleName() + "[" + getJid() + "," + getMailbox() + "]";
  }
}
