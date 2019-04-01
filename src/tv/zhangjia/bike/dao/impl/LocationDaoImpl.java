package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;

public class LocationDaoImpl implements LocationDao {
	private List<Location> locations = Database.LOCATIONS;
	private List<Bike> bikes = Database.BIKES;

	@Override
	public List<Location> queryAll() {
		return locations;
	}

	@Override
	public Location queryLocation(int id) {
		for (Location location : locations) {
			if (location.getId() == id) {
				return location;
			}
		}
		return null;
	}

	@Override
	public int doInsert(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doUpdate(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location randomLocation(int locationId, int bikeId, int leaseRecordId) {
		Random ran = new Random();
		int index = ran.nextInt(locations.size());
		// ���ѡ��һ��λ��
		int endId = locations.get(index).getId();
		// ������λ�ú͵�ǰλ����ͬ����ô����ѡ��

		while (locationId == endId) {
			index = ran.nextInt(locations.size());
			endId = locations.get(index).getId();
		}

		return queryLocation(endId);

	}

	@Override
	public String queryLocationName(int locationId) {
		Location l = queryLocation(locationId);
		return l.getLocation();
	}

	@Override
	public List<String> dispatch() {
		List<String> arr = new ArrayList<>();
	/*	StringBuilder arr = new StringBuilder();
		List<Integer> small = new ArrayList<>();
		List<Integer> big = new ArrayList<>();
		List<Integer> locationId = new ArrayList<>();
		int  average = bikes.size() / queryAll().size();
		
		
		for (Location lo : locations) {
			if(lo.getBikes().size() - average > 0) {
				big.add(lo.getBikes().size());
			} else if(lo.getBikes().size() - average < 0){
				
				small.add(lo.getBikes().size());
			}
		}
		
		Collections.sort(small);
		Collections.sort(big);
	
		
		for (int i = big.size() - 1; i >= 0; i--) {
			for (int j = 0; j < small.size(); j++) {
				int more = big.get(i) - average;
				int need = average - small.get(j);
				int cost = more - need;
				if(cost >= 0) {
					big.set(i, big.get(i) - need );
					small.set(j, small.get(j) + need);
					arr.append("��" + "�ó�" + need + "����������");
					
				} else {
					big.set(i, big.get(i) - more );
					small.set(j, small.get(j) + more);
					break;
				}
				
			}
		}		
		
		System.out.println(big);
		System.out.println(small);*/
		
		
		
		int  average = bikes.size() / queryAll().size();
		Map<Integer, Integer> small = new TreeMap<>();
		Map<Integer, Integer> big = new TreeMap<>();
		for (Location lo : locations) {
			if(lo.getBikes().size() - average > 0) {
				big.put(lo.getId(), lo.getBikes().size());
			} else if(lo.getBikes().size() - average < 0){
				small.put(lo.getId(),lo.getBikes().size());
			}
		}

		List<Map.Entry<Integer, Integer>> smalls = new ArrayList<Map.Entry<Integer, Integer>>(small.entrySet());
		// Ȼ��ͨ���Ƚ�����ʵ������
		Collections.sort(smalls, new Comparator<Map.Entry<Integer, Integer>>() {
			// ��������
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		
		
		List<Map.Entry<Integer, Integer>> bigs = new ArrayList<Map.Entry<Integer, Integer>>(big.entrySet());
		// Ȼ��ͨ���Ƚ�����ʵ������
		Collections.sort(bigs, new Comparator<Map.Entry<Integer, Integer>>() {
			// ��������
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		
		System.out.println(bigs);
		System.out.println(smalls);
		
		for (int i = bigs.size() - 1; i >= 0; i--) {
			for (int j = 0; j < smalls.size(); j++) {
				int more = bigs.get(i).getValue() - average;
				System.out.println("more = " + more);
//				int more = big.get(i) - average;
				int need = average - smalls.get(j).getValue();
				System.out.println("need = " + need);
				int cost = more - need;
				System.out.println("cost = " + cost);
				if(cost >= 0) {
					bigs.get(i).setValue(bigs.get(i).getValue() - need);
					smalls.get(j).setValue(smalls.get(j).getValue() + need);
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("��" + bigname + "�ó�" + need + "����������" + smallname);
					
				} else {
					bigs.get(i).setValue(bigs.get(i).getValue() - more);
					smalls.get(j).setValue(smalls.get(j).getValue() + more);
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("��" + bigname + "�ó�" + need + "����������" + smallname);
					break;
				}
				
			}
		}		
		

		System.out.println(bigs);
		System.out.println(smalls);
		
		
		
		return arr;
	}
/*
	@Override
	public int changeBikeLocation(int bikeId, int locationId, int oldLocationId) {
		for (Bike bike : bikes) {
			if (bike.getId() == bikeId) {
				// �ҵ���������ɽ裬��ô�Ӹ�λ����ȥ���ó�
				if (bike.getStatus() == 1) {
					Location l = queryLocation(oldLocationId);
					l.getBikes().remove(bike);
					return 1;
				} else {
					// ������ɽ裬���ӵ���λ��
					queryLocation(locationId).getBikes().add(bike);
					return 1;
				}
			}
		}

		return 0;
	}

	@Override
	public int addBikeLocation(int bikeId, int locationId) {
		for (Bike bike : bikes) {
			if (bike.getId() == bikeId) {

				queryLocation(locationId).getBikes().add(bike);
			}
		}

		return 0;
	}*/

	@Override
	public boolean updateLocationBikes(int locationId) {
		List<Bike> b = new ArrayList<>();
		for (Bike bike : bikes) {
			if(bike.getLocationId() == locationId) {
				b.add(bike);
			} 
		}
		
		queryLocation(locationId).setBikes(b);
		return true;
	}
	
	
	@Override
	public boolean deleteLocationBikes(int locationId,int bikeID) {
		BikeDao bikeDao = new BikeDaoImpl();
		//��ȡ��λ�õ���Ϣ
		Location lo = queryLocation(locationId);
		Bike bike = bikeDao.queryById(bikeID);
		
		lo.getBikes().remove(bike);
		return true;
	}

}