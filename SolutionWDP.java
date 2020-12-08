package sample;

import java.util.ArrayList;
import java.util.Iterator;

public class SolutionWDP {
FormuleWDP instance;
    private ArrayList<Bid> bids;
    private ArrayList<Bid> conflict;
    private double gain;
    private short diversite;

    SolutionWDP(FormuleWDP instance){
        this.instance=instance;
        bids=new ArrayList<Bid>();
        conflict=new ArrayList<Bid>();
        this.gain=0;
        this.diversite=0;
    }
    SolutionWDP(){

    }

    public FormuleWDP getInstance() {
        return instance;
    }

    public void setDiversite(short diversite) {
        this.diversite = diversite;
    }


  public void setDiversite(ArrayList<SolutionWDP> listeTaboue){
        short diversite=(short)this.bids.size();
  Iterator<SolutionWDP> LT=listeTaboue.iterator();
        while(LT.hasNext()){
            SolutionWDP tmp=LT.next();
            short distance=0;
  for(int i=0;i<this.bids.size() && i<tmp.getBids().size();i++){
                if(!this.bids.get(i).equals(tmp.getBids().get(i))){
                    distance++;
                }
                distance+=Math.abs(this.bids.size()-tmp.getBids().size());
            }
            if(distance<diversite){
                diversite=distance;
            }
        }
        this.diversite=diversite;
  }

    public SolutionWDP genererRandom(){
        SolutionWDP s=new SolutionWDP(instance);

        ArrayList<Integer> r=new ArrayList<Integer>();
        for(int i=0;i<this.instance.getNbBids();i++){
            int tmp=(int)(Math.random()*10000)%this.instance.getNbLots();
            r.add(tmp);
        }

        int i=this.instance.getNbBids()-1;
        while(i>=0){
            int index=r.indexOf(i);
            while(index==-1){
                i--;
                index=r.indexOf(i);

            }
            Bid tmp=this.instance.getBids().get(index);
            r.set(index,-1);

            if(!tmp.isInConflict(s.bids)){
                s.addBid(tmp);
            }
        }
        return s;
    }


    public SolutionWDP clone(){
        SolutionWDP solution=new SolutionWDP(instance);

        Iterator<Bid> bids=this.getBids().iterator();

        while(bids.hasNext()){
            solution.addBid(bids.next());
        }
        solution.setDiversite(this.diversite);
        return solution;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public short getDiversite() {
        return diversite;
    }

    public double getGain() {
        return gain;
    }


    public void addBid(Bid b){
        this.bids.add(b);
        this.gain+=b.getGain();
        this.concatConflict(b);
    }
    public void concatConflict(Bid b){
        Iterator<Bid> conflictIterator=b.getConflict().iterator();
        while(conflictIterator.hasNext()){
            Bid tmp=conflictIterator.next();
            if(!this.conflict.contains(tmp)){
                this.conflict.add(tmp);
            }
        }
    }


    public void forcedAddBid(Bid b){
        Iterator<Bid> bids=this.bids.iterator();
        ArrayList<Bid> toRemove=new ArrayList<Bid>();
        while(bids.hasNext()){
            Bid tmp=bids.next();
            if(b.isInConflictWith(tmp)){
                toRemove.add(tmp);
                this.gain-=tmp.getGain();
            }
        }

        Iterator<Bid> removes=toRemove.iterator();
        while(removes.hasNext()){
            this.bids.remove(removes.next());
        }

        this.setConflict();

        this.bids.add(b);
        this.concatConflict(b);
        this.gain+=b.getGain();
    }

    public void setConflict(){
        Iterator<Bid> bidsIterator=this.bids.iterator();
        while(bidsIterator.hasNext()){
            this.concatConflict(bidsIterator.next());
        }
    }


}


