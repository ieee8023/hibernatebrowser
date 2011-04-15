/* 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package joecohen.hibernatedemo;

import java.util.Date;

import joecohen.hibernatedemo.pojos.Client;
import joecohen.hibernatedemo.pojos.Matter;
import joecohen.hibernatedemo.pojos.Noun;
import joecohen.hibernatedemo.pojos.SubNoun;
import joecohen.hibernatedemo.pojos.SubSubNoun;
import joecohen.hibernatedemo.pojos.Task;
import joecohen.hibernatedemo.pojos.Time;
import joecohen.hibernatedemo.pojos.Verb;
import joecohen.hibernatedemo.pojos.VerbNounRelation;

import org.hibernate.Session;

public class AddData {

	public static void main(String[] args){
		
		
		Session session = HibUtil.startTransaction();
		
		Client client = new Client(111L, "Joseph Paul Cohen");
		Matter matter = new Matter(222L, "A Matter");
		session.save(matter);
		client.getMatters().add(matter);
		session.save(client);
	
		VerbNounRelation vnr = new VerbNounRelation();
		vnr.setVerb(new Verb("A Verb"));
		vnr.setNoun(new Noun("A Noun"));
		vnr.setSubNoun(new SubNoun("A SubNoun"));
		vnr.setSubSubNoun(new SubSubNoun("A SubSubNoun"));
		session.save(vnr);
		
		
		Time t1 = new Time(new Date(new Date().getTime() - 1000));
		t1.setEndTime(new Date(new Date().getTime() + 1000));
		session.save(t1);
		
		Time t2 = new Time(new Date(new Date().getTime() - 3000));
		t2.setEndTime(new Date(new Date().getTime() + 3000));
		session.save(t2);
		
		Time t3 = new Time(new Date(new Date().getTime() - 10000));
		t3.setEndTime(new Date(new Date().getTime() + 10000));
		session.save(t3);
		
		session.flush();
		HibUtil.commitTransaction();
		HibUtil.shutdown();
		System.out.println("Done");
		
		// this will open a GUI for the db
		//HibUtil.openDBEditor();
		
	}
}
