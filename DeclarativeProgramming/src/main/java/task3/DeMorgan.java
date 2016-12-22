package task3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeMorgan {

	public static <T> boolean deMorganLaws(Set<T> set, Map<Pair<T, T>, T> operator1, Map<Pair<T, T>, T> operator2,
			Map<T, T> operator3) {
		return set.stream().
				   allMatch(x -> set.stream().
									 allMatch(y -> firstLaw(x, y, operator1, operator2, operator3) &&
											 	   secondLaw(x, y, operator1, operator2, operator3)));
	}

	private static <T> boolean firstLaw(T x, T y, Map<Pair<T, T>, T> operator1, Map<Pair<T, T>, T> operator2,
			Map<T, T> operator3) {
		Pair<T, T> current = new Pair<>(x, y);
		return operator3.get(operator1.get(current))
				.equals(operator2.get(new Pair<>(operator3.get(x), operator3.get(y))));
	}

	private static <T> boolean secondLaw(T x, T y, Map<Pair<T, T>, T> operator1, Map<Pair<T, T>, T> operator2,
			Map<T, T> operator3) {
		Pair<T, T> current = new Pair<>(x, y);
		return operator3.get(operator2.get(current))
				.equals(operator1.get(new Pair<>(operator3.get(x), operator3.get(y))));
	}

	public static void main(String[] args) {
		Set<Boolean> set = new HashSet<>();
		set.add(false);
		set.add(true);

		Map<Pair<Boolean, Boolean>, Boolean> logicalAnd = new HashMap<>();
		logicalAnd.put(new Pair<>(true, true), true);
		logicalAnd.put(new Pair<>(false, true), false);
		logicalAnd.put(new Pair<>(true, false), false);
		logicalAnd.put(new Pair<>(false, false), false);

		Map<Pair<Boolean, Boolean>, Boolean> logicalOr = new HashMap<>();
		logicalOr.put(new Pair<>(true, true), true);
		logicalOr.put(new Pair<>(false, true), true);
		logicalOr.put(new Pair<>(true, false), true);
		logicalOr.put(new Pair<>(false, false), false);

		Map<Boolean, Boolean> logicalNot = new HashMap<>();
		logicalNot.put(true, false);
		logicalNot.put(false, true);
		
		Map<Boolean, Boolean> identity = new HashMap<>();
		identity.put(true, true);
		identity.put(false, false);
		
		
		System.out.println(deMorganLaws(set, logicalAnd, logicalOr, logicalNot));
		System.out.println(deMorganLaws(set, logicalAnd, logicalOr, identity));

	}
}
