package com.jhhy.cuiweitourism;

/**
 * Created by jiahe008 on 2016/9/23.
 */
public interface IOrderItemClick
{
    /**
     * 订单列表item单击事件
     * @param position 此item位于列表中的位置
     */
    public void onOrderItemClick(int position);
    /**
     * 取消签约
     * @param position
     */
//    public void onCancelOrderClick(int position);

    /**
     * 签约付款
     * @param position
     */
//    public void onPayClick(int position);

    /**
     * 去评价
     * @param position
     */
//    public void onCommentClick(int position);

    /**
     * 申请退款
     * @param position
     */
//    public void onRefundClick(int position);

    /**
     * 取消退款
     * @param position
     */
//    public void onCancelRefundClick(int position);
}
