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
		// 随机选择一个位置
		int endId = locations.get(index).getId();
		// 如果这个位置和当前位置相同，那么重新选择

		while (locationId == endId) {
			index = ran.nextInt(locations.size());
			endId = locations.get(index).getId();
		}

		return queryLocation(endId);

	}
	
	@Override
	public Location randomUserLocation() {
		Random ran = new Random();
		int index = ran.nextInt(locations.size());
		// 随机选择一个位置
		Location lo = locations.get(index);
		// 如果这个位置和当前位置相同，那么重新选择

		return lo;

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
					arr.append("从" + "拿出" + need + "辆单车送往");
					
				} else {
					big.set(i, big.get(i) - more );
					small.set(j, small.get(j) + more);
					break;
				}
				
			}
		}		
		
		System.out.println(big);
		System.out.println(small);*/
		double size = 0;
		for (Bike bike : bikes) {
			if(bike.getStatus() != 0) {
				size++;
			}
		}
		System.out.println("一共有" + size);
	
		double  a = size / queryAll().size(); //自行车个数除以位置总数
		System.out.println("size =" + size);
		System.out.println("a = " +a);
		int  average = (int) (Math.ceil(a));
		
		System.out.println("平均数" + average);
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
		// 然后通过比较器来实现排序
		Collections.sort(smalls, new Comparator<Map.Entry<Integer, Integer>>() {
			// 升序排序
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		
		
		List<Map.Entry<Integer, Integer>> bigs = new ArrayList<Map.Entry<Integer, Integer>>(big.entrySet());
		// 然后通过比较器来实现排序
		Collections.sort(bigs, new Comparator<Map.Entry<Integer, Integer>>() {
			// 升序排序
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		
		System.out.println("s = " + bigs);
		System.out.println("s = " + smalls);
		
		
		//多的位置车辆总数：A  
		//少的位置车辆总数：B
		int x  = 0;
		for (int i = bigs.size() - 1; i >= 0; i--,x++) {
			
			for (int j = x; j < smalls.size(); j++) {
				int more = bigs.get(i).getValue() - average; //A比平均数多出来多少辆
//				System.out.println("more = " + more);
//				int more = big.get(i) - average;
				int need = average - smalls.get(j).getValue(); // B比平均数少多少辆
//				System.out.println("need = " + need);
				int cost = more - need; // A 需要给B 多少辆
//				System.out.println("cost = " + cost);
				
				if(more == 0) {
					break;
				}
				
				//如果B需要的车辆 < A
				if(cost >= 0) {
					System.out.println(">0");
					bigs.get(i).setValue(bigs.get(i).getValue() - need);  //A给B所需要的所有车辆
					smalls.get(j).setValue(smalls.get(j).getValue() + need); //B加上A给的的车辆
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("从" + bigname + "拿出" + need + "辆单车送往" + smallname);
					
				//如果A中还有剩余车辆，但是无法满足B的所有需求
				} else {
	
					System.out.println("<0");
					bigs.get(i).setValue(bigs.get(i).getValue() - more); //A把能给的都给B
					smalls.get(j).setValue(smalls.get(j).getValue() + more); //B加上A给的车辆
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("从" + bigname + "拿出" + more + "辆单车送往" + smallname);
					break;
				}
				System.out.println("内循环");
				
			}
			
			System.out.println("ing = " + bigs);
			System.out.println("ing = " + smalls);
			
		}		
		
//
		System.out.println("end = " + bigs);
		System.out.println("end = " + smalls);
		
		
		
		return arr;
	}
/*
	@Override
	public int changeBikeLocation(int bikeId, int locationId, int oldLocationId) {
		for (Bike bike : bikes) {
			if (bike.getId() == bikeId) {
				// 找到车后，如果可借，那么从该位置中去掉该车
				if (bike.getStatus() == 1) {
					Location l = queryLocation(oldLocationId);
					l.getBikes().remove(bike);
					return 1;
				} else {
					// 如果不可借，添加到新位置
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
		//获取该位置的信息
		Location lo = queryLocation(locationId);
		Bike bike = bikeDao.queryById(bikeID);
		
		lo.getBikes().remove(bike);
		return true;
	}

}
