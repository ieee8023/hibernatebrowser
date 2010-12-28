package joecohen.hibernatebrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.hibernate.EntityMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.proxy.HibernateProxy;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

public class HibernateBrowserGUI implements TreeModel{	
	private static final long serialVersionUID = 1L;
	private String databaseName;
	private Node root = new ObjectNode("Loading...");
	private static String utilityName = "NonPolymorphic Hibernate Browser - Joseph P. Cohen";
	JTree tree = null;
	private List<TreeModelListener> treeListeners = new ArrayList<TreeModelListener>();
	private Session session = null;
	private DebugConfig dconf = null; 
	private HibernateUtil util = null;
	JProgressBar progressBar = null;
	
	public static void main(String[] args) {
		
		System.out.println(utilityName);
		
		HibernateBrowserGUI gui = new HibernateBrowserGUI();
		
	}
	
	
	public HibernateBrowserGUI(){
		
    	Properties props = new Properties();
    	try {
			props.load(HibernateUtil.class.getResourceAsStream("HibernateBrowser.properties"));
		} catch (IOException e) {
			System.out.println("HibernateBrowser.properties not found");
		}
		databaseName = props.getProperty("databaseName", "default");
		
		dconf = HibernateUtil.debugConfigs.get(databaseName);

		
		
		/// start up GUI
		
		JFrame frame = new JFrame();
		frame.setTitle(utilityName + " - " + databaseName);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		
		JTree tree = new JTree(this);
		JScrollPane treePanel = new JScrollPane(tree);
		
		
		
		//toolPanel
		JPanel toolPanel = new JPanel();
		toolPanel.add(new JButton("Show Entire Database")).addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						viewDB();
						
					}
				};
				Thread thread = new Thread(runnable); 
				thread.start(); 
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		
		toolPanel.add(new JLabel(" - or - "));
		final JTextArea textArea = new JTextArea();
		toolPanel.add(textArea).setPreferredSize(new Dimension(250, 20));
		toolPanel.add(new JButton("Search")).addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						searchDB(textArea.getText());
						
					}
				};
				Thread thread = new Thread(runnable); 
				thread.start(); 
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		JPanel hqlPanel = new JPanel();
		
		
		final JTextArea hqlArea = new JTextArea("SELECT item FROM java.lang.Object item");
		hqlPanel.add(hqlArea).setPreferredSize(new Dimension(650, 40));
		hqlPanel.add(new JButton("Execute")).addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						executeHQL(hqlArea.getText());
						
					}
				};
				Thread thread = new Thread(runnable); 
				thread.start(); 
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		
		//progressbar
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		
		JPanel topPanel = new JPanel(new GridLayout(3,1));
		topPanel.add(toolPanel);
		topPanel.add(hqlPanel);
		topPanel.add(progressBar);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(treePanel);
		
		frame.add(mainPanel);
		
		frame.setVisible(true);
		
		System.out.println("Loaded");

	}
	
	private Session openDB(){
			progressBar.setValue(10);
			progressBar.setString("Opening DB..");
			util = new HibernateUtil(this.dconf);
			progressBar.setValue(30);
			SessionFactory sessionfactory = util.getSessionFactory();
			progressBar.setValue(40);
			Session s = sessionfactory.openSession();
			progressBar.setValue(50);
			progressBar.setString("");
			return s;	
	}
	
	private void viewDB(){
		try{
			if (session == null)
				session = openDB();
			progressBar.setValue(70);
			progressBar.setString("Building Tree..");
			root = new RootNode(databaseName, session, util);
			progressBar.setValue(80);
			for (TreeModelListener l : treeListeners)
				l.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
			progressBar.setValue(0);
			progressBar.setString("");
		}catch(Exception e){
			root = new ObjectNode(e);
			for (TreeModelListener l : treeListeners)
				l.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
			progressBar.setString("Error");
			progressBar.setValue(0);
		}
	}
	
	private void searchDB(String term){
		try{
			if (session == null)
				session = openDB();
			progressBar.setValue(70);
			progressBar.setString("Building Tree..");
			root = new SearchNode(term, session, util);
			progressBar.setValue(80);
			for (TreeModelListener l : treeListeners)
				l.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
			progressBar.setValue(0);
			progressBar.setString("");
		}catch(Exception e){
			root = new ObjectNode(e);
			for (TreeModelListener l : treeListeners)
				l.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
			progressBar.setString("Error");
			progressBar.setValue(0);
		}
	}
	
	private void executeHQL(String hql){
		try{
			if (session == null)
				session = openDB();
			progressBar.setValue(70);
			progressBar.setString("Building Tree..");
			root = new HQLNode(hql, session, util);
			progressBar.setValue(80);
			for (TreeModelListener l : treeListeners)
				l.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
			progressBar.setValue(0);
			progressBar.setString("");
		}catch(Exception e){
			root = new ObjectNode(e);
			for (TreeModelListener l : treeListeners)
				l.treeStructureChanged(new TreeModelEvent(this, new TreePath(root)));
			progressBar.setString("Error");
			progressBar.setValue(0);
		}
	}
	
	@Override
	public Object getRoot() {
		return root;
	}


	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof Node)
			return ((Node)parent).getChild(index);
		else
			return null;
	}


	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof Node)
			return ((Node)parent).getChildCount();
		else
			return 0;
	}


	@Override
	public boolean isLeaf(Object node) {
		if (node instanceof Node)
			return ((Node)node).getChildCount() == 0;
		else
			return true;
	}


	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof Node){
			for(int i = 0;i < ((Node)parent).getChildCount();i++)
				if (child.equals(((Node)parent).getChild(i)))
						return i;
		}
		return -1;
	}


	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeListeners.add(l);
		
	}


	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeListeners.remove(l);
		
	}
	
	
}


interface Node{
	public int getChildCount();
	public Node getChild(int index);
}

class RootNode implements Node{
	
	public List<ClassNode> classList = new ArrayList<ClassNode>();
	
	String name;
	public RootNode(String name, Session session, HibernateUtil util){
		this.name = name;
		
		for (ClassMetadata meta : util.getMappedClasses()){
			Class clazz = meta.getMappedClass(EntityMode.POJO);
			this.classList.add(new ClassNode(clazz, session));
		}
		
		List<ClassNode> tempClassList = new ArrayList<ClassNode>(this.classList);
		
		for (ClassNode cno : tempClassList){
			for (ClassNode cni : tempClassList){
				if (cno.clazz.getSuperclass().equals(cni.clazz)){
					cni.classList.add(cno);
					this.classList.remove(cno);
				}
			}
			
		}
		
		
		
	}
	
	public String toString(){
		return name;
	}

	@Override
	public int getChildCount() {
		return classList.size();
	}

	@Override
	public Node getChild(int index) {
		return classList.get(index);
	}
}

class SearchNode implements Node{
	
	public List<ObjectNode> objectList;
	String term;
	Session session;
	HibernateUtil util;
	
	public SearchNode(String term, Session session, HibernateUtil util){
		this.term = term;
		this.session = session;
		this.util = util;
	}
	
	private  List<ObjectNode> getObjectList(){
		List<ObjectNode> objectList = new ArrayList<ObjectNode>();
		
		Query q = session.createQuery("SELECT item FROM java.lang.Object item");
		
		for(Iterator it=q.iterate();it.hasNext();){
			try{
				Object o = it.next();
				ObjectNode on = new ObjectNode(o);
				if (on.toString().contains(term) || on.containsString(term))
					objectList.add(on);
			}catch(EncryptionOperationNotPossibleException e){
				objectList.add(new ObjectNode("Error <" + e.getMessage() + ">"));
				//System.out.println("Error <" + e.getMessage() + ">");
				//e.printStackTrace();
			}catch(NumberFormatException e){
				objectList.add(new ObjectNode("Error <" + e.getMessage() + ">"));
				//System.out.println("Error <" + e.getMessage() + ">");
				//e.printStackTrace();
			}
		}
		
		return objectList;
	}
	
	public String toString(){
		return "Search for term: <" + term + ">";
	}

	@Override
	public int getChildCount() {
		if (objectList == null)
			objectList = getObjectList();
		return objectList.size();
	}

	@Override
	public Node getChild(int index) {
		if (objectList == null)
			objectList = getObjectList();
		return objectList.get(index);
	}
}

class HQLNode implements Node{
	
	public List<ObjectNode> objectList;
	String hql;
	Session session;
	HibernateUtil util;
	
	public HQLNode(String hql, Session session, HibernateUtil util){
		this.hql = hql;
		this.session = session;
		this.util = util;
	}
	
	private  List<ObjectNode> getObjectList(){
		List<ObjectNode> objectList = new ArrayList<ObjectNode>();
		
		Query q = session.createQuery(this.hql);
		
		for(Iterator it=q.iterate();it.hasNext();){
			try{
				Object o = it.next();
				ObjectNode on = new ObjectNode(o);
				objectList.add(on);
			}catch(EncryptionOperationNotPossibleException e){
				objectList.add(new ObjectNode("Error <" + e.getMessage() + ">"));
				//System.out.println("Error <" + e.getMessage() + ">");
				//e.printStackTrace();
			}catch(NumberFormatException e){
				objectList.add(new ObjectNode("Error <" + e.getMessage() + ">"));
				//System.out.println("Error <" + e.getMessage() + ">");
				//e.printStackTrace();
			}
		}
		
		return objectList;
	}
	
	public String toString(){
		return "Executing HQL: <" + hql + ">";
	}

	@Override
	public int getChildCount() {
		if (objectList == null)
			objectList = getObjectList();
		return objectList.size();
	}

	@Override
	public Node getChild(int index) {
		if (objectList == null)
			objectList = getObjectList();
		return objectList.get(index);
	}
}


class ClassNode implements Node{
	
	public List<ClassNode> classList = new ArrayList<ClassNode>();
	public List<ObjectNode> objectList;
	Session session;
	
	Class clazz;
	public ClassNode(Class clazz, Session session){
		this.clazz = clazz;
		this.session = session;
	}
	
	private  List<ObjectNode> getObjectList(){
		List<ObjectNode> objectList = new ArrayList<ObjectNode>();
		
		Query q = session.createQuery("SELECT item FROM " + clazz.getSimpleName() + " item");
		
		for(Iterator it=q.iterate();it.hasNext();){
			try{
				Object o = it.next();
				if (clazz.equals(o.getClass()))	
					objectList.add(new ObjectNode(o));
			}catch(EncryptionOperationNotPossibleException e){
				objectList.add(new ObjectNode("Error <" + e.getMessage() + ">"));
				//System.out.println("Error <" + e.getMessage() + ">");
				//e.printStackTrace();
			}catch(NumberFormatException e){
				objectList.add(new ObjectNode("Error <" + e.getMessage() + ">"));
				//System.out.println("Error <" + e.getMessage() + ">");
				//e.printStackTrace();
			}
		}
		
		return objectList;
	}
	
	public String toString(){
		return clazz.getCanonicalName();
	}

	@Override
	public int getChildCount() {
		if (objectList == null)
			objectList = getObjectList();
		return objectList.size() + classList.size();
	}

	@Override
	public Node getChild(int index) {
		if (objectList == null)
			objectList = getObjectList();
		if (index < classList.size())
			return classList.get(index);
		else
			return objectList.get(index - classList.size());
	}
}


class ObjectNode implements Node{
	
	Object o;
	List<Method> methods = new ArrayList<Method>();
	List<Method> varList = new ArrayList<Method>();
	
	public ObjectNode(Object o){
		
		if (o instanceof HibernateProxy){
			try{
				o  = ((HibernateProxy)o).getHibernateLazyInitializer().getImplementation();
			}catch(Throwable t){
				o = "Error <" + t.getMessage() + ">";
				t.printStackTrace();
			}
		}
		
		this.o = o;
		
		if (o != null){
			// we check to see if we don't have a primitive or boxed type
			if (!o.getClass().isPrimitive() && !(o instanceof String || o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Long)){
				for(Class temp = o.getClass(); temp != Object.class; temp = temp.getSuperclass())
					for(Method m : temp.getDeclaredMethods())
						methods.add(m);
					
				    for (Method m : methods)
				    	if ((m.getName().contains("get") || m.getName().contains("is")) && 
				    			m.getParameterTypes().length == 0 && 
				    			!Modifier.isStatic(m.getModifiers()) &&
				    			!Modifier.isPrivate(m.getModifiers()) &&
				    			!Modifier.isNative(m.getModifiers()) &&
				    			!Modifier.isProtected(m.getModifiers()))
				    		varList.add(m);
			}
		}
	}

	public String toString(){
		if (o != null)
			return this.o.getClass().getSimpleName() + " - " + this.o.toString();
		else
			return "null";
	}

	@Override
	public int getChildCount() {
		return varList.size();
	}

	@Override
	public Node getChild(int index) {
		return new MethodNode(varList.get(index), o);
	}
	
	public Boolean containsString(String term){
		
		for (int i = 0 ; i < this.getChildCount() ; i++){
			Node target = this.getChild(i);
			if(target != null && target.toString().toLowerCase().contains(term.toLowerCase()))
				return true;
		}
		return false;

	}
	
}

class MethodNode implements Node{
	
	Node node;
	Method m;
	
	public MethodNode(Method m, Object o){
		this.m = m;
		try {
			Object obj = m.invoke(o, new Object[0]);
			if (obj instanceof Collection)
				node = new CollectionNode((Collection)obj);
			else
				node = new ObjectNode(obj);
		} catch (Throwable e) {
			e.printStackTrace();
			node = new ObjectNode("Error <" + e.getMessage() + ">");
		}
	}
	
	public String toString(){
		return m.getName() + " - " + node;
	}

	@Override
	public int getChildCount() {
		return node.getChildCount();
	}

	@Override
	public Node getChild(int index) {
		return node.getChild(index);
	}
}

class CollectionNode implements Node{
		
		@SuppressWarnings("rawtypes")
		Collection c;
		Object[] o  = {};
		
		@SuppressWarnings("rawtypes")
		public CollectionNode(Collection c){
			
			if (c instanceof HibernateProxy){
				c  = (Collection) ((HibernateProxy)c).getHibernateLazyInitializer().getImplementation();
			}

			if (c != null)
				this.o = c.toArray();
		}

		public String toString(){
			if (c != null)
				return this.c.getClass().getSimpleName();
			else
				return "Collection";
		}

		@Override
		public int getChildCount() {
			return o.length;
		}

		@Override
		public Node getChild(int index) {
			if (o[index] instanceof Collection)
				return new CollectionNode((Collection)o[index]);
			else
				return new ObjectNode(o[index]);
		}
	}
