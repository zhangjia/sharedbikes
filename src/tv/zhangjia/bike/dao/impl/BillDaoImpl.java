package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bill;

public class BillDaoImpl implements BillDao{
	private List<Bill> bills = Database.BILLS;
	@Override
	public boolean doInsert(int userId, String event, double money) {
		Bill bill  = new Bill(Database.nextBillId(),event,userId,new Date(),money);
		return bills.add(bill);
	}

	@Override
	public List<Bill> queryAll() {
		return bills;
	}

	@Override
	public Bill queryByBillId(int billId) {
		for (Bill bill : bills) {
			if(bill.getId() == billId) {
				return bill;
			}
		}
		return null;
	}

	@Override
	public List<Bill> queryUserBill(int userId) {
		List<Bill> newBill = new ArrayList<>();
		for (Bill bill : bills) {
			if(bill.getUserId() == userId) {
				newBill.add(bill);
			}
		} 
		return newBill;
	}
	
}
