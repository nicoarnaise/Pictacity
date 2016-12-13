package ui;

import java.util.LinkedList;

import fonctions.Traitement;

public class PileTraitement {
	
	private LinkedList<Traitement> queue;
	private static PileTraitement queueT;
	
	private PileTraitement(){
		queue = new LinkedList<Traitement>();
	}
	
	public static PileTraitement getInstance(){
		if(queueT == null){
			queueT = new PileTraitement();
		}
		return queueT;
	}
	
	public void addQueue(Traitement t){
		queue.add(t);
	}
	
	public void removeQueue(int index){
		LinkedList<Traitement> temp = new LinkedList<Traitement>();
		int maxi = queue.size();
		for(int i =0;i<maxi;i++){
			if(i!=index){
				temp.add(queue.poll());
			}else{
				queue.poll();
			}
		}
		queue.addAll(temp);
	}
	
	public LinkedList<Traitement> getQueue() {
		return queue;
	}

	public void upQueue(int index){
		LinkedList<Traitement> temp = new LinkedList<Traitement>();
		int maxi = queue.size();
		for(int i =0;i<maxi;i++){
			if(i==index-1){
				Traitement t = queue.poll();
				temp.add(queue.poll());
				temp.add(t);
				i++;
			}else{
				temp.add(queue.poll());
			}
		}
		queue.addAll(temp);
	}
	
	public void downQueue(int index){
		LinkedList<Traitement> temp = new LinkedList<Traitement>();
		int maxi = queue.size();
		for(int i =0;i<maxi;i++){
			if(i==index){
				Traitement t = queue.poll();
				temp.add(queue.poll());
				temp.add(t);
				i++;
			}else{
				temp.add(queue.poll());
			}
		}
		queue.addAll(temp);
	}
	
}
