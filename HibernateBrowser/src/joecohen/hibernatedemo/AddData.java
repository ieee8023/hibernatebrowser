package joecohen.hibernatedemo;

import joecohen.hibernatedemo.pojos.Client;
import joecohen.hibernatedemo.pojos.Matter;
import joecohen.hibernatedemo.pojos.Noun;
import joecohen.hibernatedemo.pojos.SubNoun;
import joecohen.hibernatedemo.pojos.SubSubNoun;
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
		
		session.flush();
		HibUtil.commitTransaction();
		HibUtil.shutdown();
		System.out.println("Done");
		
		// this will open a GUI for the db
		//HibUtil.openDBEditor();
		
	}
}
