package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GA extends  SolutionWDP {

    private FormuleWDP instance;

    @Override
    public ArrayList<Bid> getBids() {
        return super.getBids();
    }

    public FormuleWDP getInstance() {
        return instance;
    }


    public GA(FormuleWDP instance) {

        this.instance = instance;
    }

    ArrayList<SolutionWDP> genratePopulationSolution(int sizeOfPopultion) {

        SolutionWDP solution = new SolutionWDP(instance);
        ArrayList<SolutionWDP> randomGen = new ArrayList<>();

        for (int i = 0; i < sizeOfPopultion; i++) {
            randomGen.add(solution.genererRandom());
        }
   return randomGen;


    }

    public ArrayList<Double> fitness(ArrayList<SolutionWDP> solutions) {
        ArrayList<Double> results = new ArrayList<>();
        int i = 0;
        while (i < solutions.size()) {
            Double j = solutions.get(i).getGain();

            results.add(j);
            i++;
        }
        return results;
    }

    public ArrayList<SolutionWDP> selection(ArrayList<SolutionWDP> solutions) {//normally we chosse number equals to two to compare two parents
         ArrayList<SolutionWDP> c1=getHighFitness(solutions);
        ArrayList<SolutionWDP> V=otherC2(solutions);


 for(int i=0;i<V.size();i++){
V.get(i).setDiversite(c1);
    if(V.get(i).getDiversite()==0) c1.add(V.get(i));
}

return c1;
    }

    public SolutionWDP croisement(SolutionWDP solutionWDP,SolutionWDP solutionWDP1) {


        SolutionWDP child=new SolutionWDP(instance);
           for(int j=0;j<solutionWDP.getBids().size();j++){

                if(!child.getBids().equals(solutionWDP.getBids()))

               {
          if(solutionWDP.getBids().get(j).isInConflict(child.getBids())==false) child.forcedAddBid(solutionWDP.getBids().get(j));

                }
            }

SolutionWDP l=new SolutionWDP(instance);
  for(int i=0;i<child.getBids().size();i++){
       for(int j=0;j<solutionWDP1.getBids().size();j++){


if(solutionWDP1.getBids().get(j).isInConflictWith(child.getBids().get(i))==false && solutionWDP1.getBids().get(j).equals(child.getBids().get(i))==false

   && l.getBids().contains(solutionWDP1.getBids().get(j))==false    && l.getBids().contains(child.getBids().get(i))==false ) l.addBid(solutionWDP1.getBids().get(j));

           }

   }

   for(int i=0;i<l.getBids().size();i++){
       child.addBid(l.getBids().get(i));
   }

   return child;
    }


    public SolutionWDP RechercheLocaleStochastique(double wp,int nbIterationsMax,FormuleWDP instance,SolutionWDP s) throws Exception{

        SolutionWDP best=s.clone();
        for(int nbIteration=0;nbIteration<nbIterationsMax;nbIteration++){
            Bid bid;

            double r=0+(Math.random()*(1-0));
 if(r<wp){
      bid=instance.getBids().get((int)(Math.random()*10000)%instance.getNbBids());
            }
            else{
            bid=bestBid(s);
            }
            if(bid!=null && !bid.isInConflict(s.getBids()) && !bid.equals(s.getBids())){

            s.addBid(bid);

            if(s.getGain()>best.getGain()) {
                best = s.clone();

            }
 }
        }

      //  System.out.println("best " + best.getBids().get(0));
        return best;
    }
    public Bid bestBid(SolutionWDP s){
     Iterator<Bid> bids=this.instance.getBids().iterator();
        Bid bestBid= null;
        double bestGainBid=-10000;
        while(bids.hasNext()){
            Bid tmp=bids.next();

            if(!s.getBids().contains(tmp)){
                Iterator<Bid> conflicts=tmp.getConflict().iterator();
                double perte=0;
                while(conflicts.hasNext()){
                    Bid tmpConflinct=conflicts.next();
                    if(s.getBids().contains(tmpConflinct)){
                        perte+=tmpConflinct.getGain();
                    }
                }
                double gainBid=tmp.getGain()-perte;
                if(gainBid>bestGainBid){
                    bestGainBid=gainBid;
                    bestBid=tmp.clone();
                }
            }

        }
       return bestBid;
    }

ArrayList<SolutionWDP> getHighFitness(ArrayList<SolutionWDP> population){
        ArrayList<SolutionWDP> listeOfHighFitness=new ArrayList<>();
        //choose 2 best fitness values

 ArrayList<Double> liste=fitness(population);
 Collections.sort(liste);
 Collections.reverse(liste);
for(int i=0;i<population.size();i++){
    if(population.get(i).getGain()==liste.get(0) || population.get(i).getGain()==liste.get(1)) listeOfHighFitness.add(population.get(i));

}
        return listeOfHighFitness;
}

ArrayList<SolutionWDP> otherC2(ArrayList<SolutionWDP> population){

        ArrayList<SolutionWDP> c2=new ArrayList<>();
        ArrayList<Double> liste=this.fitness(population);
      Collections.sort(liste);Collections.reverse(liste);
liste.remove(0);liste.remove(1);
      for(int i=0;i<population.size();i++) {
          for (int j = 0; j < liste.size(); j++) {


              if (population.get(i).getGain() == liste.get(j))
                  c2.add(population.get(i));

          }
      }

        return c2;
}




   SolutionWDP memeticAlgo(FormuleWDP instance,int sizePop,int max,Double wp) throws Exception{
       SolutionWDP p1,best,p2,v,v1;
       int i=0;
best=new SolutionWDP(instance);
         ArrayList<SolutionWDP> population=genratePopulationSolution(sizePop);
         ArrayList<SolutionWDP> selection= this.selection(population);

while(i<max){

     p1=selection.get(0);
     p2=selection.get(1);
    v=this.croisement(p1,p2);
  v1=this.RechercheLocaleStochastique(wp,max,instance,v);

 if(v.getGain()>v1.getGain()) best=v1.clone();
else best=v.clone();
i++;}
best.setConflict();

System.out.println(best.getBids().size());
System.out.println(best.getBids().get(0));
return best;

    }
}

