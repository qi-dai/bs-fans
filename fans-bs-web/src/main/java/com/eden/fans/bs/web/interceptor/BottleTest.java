package com.eden.fans.bs.web.interceptor;

/**
 * Created by Administrator on 2016/5/27.
 */
public class BottleTest {
    public static void main(String[] args){
        int yourMoney = 6;
        TurnBeerInfo turnBeerInfo = new TurnBeerInfo();
        buyBeer(turnBeerInfo,yourMoney);
        int i = 0;
        while(turnBeerInfo.bottleNum>=2||turnBeerInfo.coverNum>=4){
            compute(turnBeerInfo,++i);
        }
        System.out.println("总共喝过"+turnBeerInfo.drinkSumNum+"瓶啤酒，还剩下"+turnBeerInfo.bottleNum+"个瓶子，"
                +turnBeerInfo.getCoverNum()+"个瓶盖");
    }

    public static void buyBeer(TurnBeerInfo turnBeerInfo,int money){
        int beerNum = money/2;
        turnBeerInfo.drinkSumNum = beerNum;
        turnBeerInfo.bottleNum = beerNum;
        turnBeerInfo.coverNum = beerNum;
    }

    public static void compute(TurnBeerInfo turnBeerInfo,int i){
        System.out.println("第"+i+"次兑换，您目前总共喝过"+turnBeerInfo.drinkSumNum+"瓶啤酒，还剩下"+turnBeerInfo.bottleNum+"个瓶子，"
                +turnBeerInfo.getCoverNum()+"个瓶盖");
        if(turnBeerInfo.bottleNum/2>0){
            int bottleTurnNum =turnBeerInfo.bottleNum/2;//用瓶子兑换了bottleTurnNum瓶酒，又产生了bottleTurnNum个瓶子，bottleTurnNum个盖子
            turnBeerInfo.drinkSumNum += bottleTurnNum;//1：累加喝过啤酒总数
            turnBeerInfo.coverNum += bottleTurnNum;//2：累加瓶盖数量
            turnBeerInfo.bottleNum = turnBeerInfo.bottleNum%2+bottleTurnNum;//3：重新计算剩余瓶子数量，请记得把刚兑换的酒产生的瓶子，也及时累加
        }
        if(turnBeerInfo.coverNum/4>0){
            int coverTurnNum = turnBeerInfo.coverNum/4;//用盖子兑换了coverTurnNum瓶酒，又产生了coverTurnNum个瓶子，coverTurnNum个盖子
            turnBeerInfo.drinkSumNum += coverTurnNum;//1：累加喝过啤酒总数
            turnBeerInfo.bottleNum += coverTurnNum;//2：累加瓶子数量
            turnBeerInfo.coverNum = turnBeerInfo.coverNum%4+coverTurnNum;//3：重新计算剩余瓶盖数量，请记得把刚兑换的酒产生的盖子，也及时累加
        }
        return ;
    }
    public static class TurnBeerInfo{
        private int bottleNum;//剩余瓶子数量，2个瓶子可换1瓶啤酒
        private int coverNum;//剩余的盖子数量，4个盖子可换1瓶啤酒
        private int drinkSumNum;

        public int getBottleNum() {
            return bottleNum;
        }
        public void setBottleNum(int bottleNum) {
            this.bottleNum = bottleNum;
        }
        public int getCoverNum() {
            return coverNum;
        }
        public void setCoverNum(int coverNum) {
            this.coverNum = coverNum;
        }
        public int getDrinkSumNum() {
            return drinkSumNum;
        }
        public void setDrinkSumNum(int drinkSumNum) {
            this.drinkSumNum = drinkSumNum;
        }
    }

}
