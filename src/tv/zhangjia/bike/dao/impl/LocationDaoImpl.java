package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.util.CommonDao;

public class LocationDaoImpl extends CommonDao implements LocationDao {
	/**
	 * 查询所有的位置信息
	 * @return
	 */
	@Override
	public List<Location> queryAll() {
		// 用sql语句计算出每个位置的车辆总数后和location表连接
		String sql = "SELECT location.*,s.amount FROM location,(SELECT count(*) amount, location_id FROM bike GROUP BY location_id HAVING location_id != -1) s WHERE location.id = s.location_id;";
		return query4BeanList(sql, Location.class);

	}

	/**
	 * 根据位置id查询单条位置信息
	 * @param id
	 * @return
	 */
	@Override
	public Location queryLocation(int id) {
		String sql = "SELECT location.*,s.amount FROM location,(SELECT count(*) amount, location_id FROM bike GROUP BY location_id HAVING location_id != -1) s WHERE location.id = s.location_id AND location.id =?";
		return query4Bean(sql, Location.class, id);
	}

	/**
	 * 生成一个随机位置
	 * @param locationId
	 * @param bikeId
	 * @param leaseRecordId
	 * @return
	 */
	@Override
	public Location randomLocation(int locationId) {
		String sql = "SELECT * FROM (SELECT * FROM location order by dbms_random.value) WHERE rownum =1 AND id != ?";
		return query4Bean(sql, Location.class, locationId);
	}

	@Override
	public Location randomUserLocation() {
		String sql = "SELECT * FROM (SELECT * FROM location order by dbms_random.value) WHERE rownum =1";
		return query4Bean(sql, Location.class);

	}

	// TODO 删除，用查询代替
	@Override
	public String queryLocationName(int locationId) {
		Location l = queryLocation(locationId);
		return l.getLocationName();
	}

	/**
	 * 调度建议
	 * @return 所有的建议信息
	 */
	@Override
	public List<String> dispatch() {
		BikeDao bikeDao = new BikeDaoImpl();
		List<Bike> bikes = bikeDao.queryAll();
		List<Location> locations = queryAll();
		List<String> arr = new ArrayList<>();

		double size = 0;
		for (Bike bike : bikes) {
			if (bike.getStatus() != 0) {
				size++;
			}
		}

		double a = size / queryAll().size(); // 自行车个数除以位置总数
		int average = (int) (Math.ceil(a));

		Map<Integer, Integer> small = new TreeMap<>();
		Map<Integer, Integer> big = new TreeMap<>();
		for (Location lo : locations) {
			List<Bike> bikesByLo = queryBikesByLocation(lo.getId());

			if (bikesByLo.size() - average > 0) {
				big.put(lo.getId(), bikesByLo.size());
			} else if (bikesByLo.size() - average < 0) {
				small.put(lo.getId(), bikesByLo.size());
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

		// 多的位置车辆总数：A
		// 少的位置车辆总数：B
		int x = 0;
		for (int i = bigs.size() - 1; i >= 0; i--, x++) {

			for (int j = x; j < smalls.size(); j++) {
				int more = bigs.get(i).getValue() - average; // A比平均数多出来多少辆
				// System.out.println("more = " + more);
				// int more = big.get(i) - average;
				int need = average - smalls.get(j).getValue(); // B比平均数少多少辆
				// System.out.println("need = " + need);
				int cost = more - need; // A 需要给B 多少辆
				// System.out.println("cost = " + cost);

				if (more == 0) {
					break;
				}

				// 如果B需要的车辆 < A
				if (cost >= 0) {
					bigs.get(i).setValue(bigs.get(i).getValue() - need); // A给B所需要的所有车辆
					smalls.get(j).setValue(smalls.get(j).getValue() + need); // B加上A给的的车辆
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("从" + bigname + "拿出" + need + "辆单车送往" + smallname);

					// 如果A中还有剩余车辆，但是无法满足B的所有需求
				} else {

					bigs.get(i).setValue(bigs.get(i).getValue() - more); // A把能给的都给B
					smalls.get(j).setValue(smalls.get(j).getValue() + more); // B加上A给的车辆
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("从" + bigname + "拿出" + more + "辆单车送往" + smallname);
					break;
				}

			}

		}

		//

		return arr;
	}
	
	/**
	 * 根据位置ID查询当前位置下的所有车辆
	 * @param locationId 位置ID
	 * @return 当前位置下的所有车辆
	 */
	@Override
	public List<Bike> queryBikesByLocation(int locationId) {
		String sql = "SELECT * FROM bike WHERE location_id = ?";
		return query4BeanList(sql, Bike.class, locationId);
	}

}
