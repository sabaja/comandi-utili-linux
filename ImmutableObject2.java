package miscellaneus;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://dev.to/monknomo/make-an-immutable-object---in-java-480n
 * 
 * @author SabatiniJa
 *
 */
public class ImmutableObject2 {

	public static void main(String[] args) {
		int index = 5;

		List<String> elements = Arrays.asList("Imm1", "Imm2", "Imm3");
		Map<String, String> couples = new HashMap<>();
		couples.put("1", "Ele1");
		couples.put("2", "Ele2");
		couples.put("3", "Ele3");

		ImmutableExe imm = new ImmutableExe.ImmutableExeBuilder()
				.setElements(elements)
				.setCouples(couples)
				.setIndex(index).build();
		
		System.out.println(imm);
	}

}

/**
 * https://www.journaldev.com/1425/builder-design-pattern-in-java
 * 
 * @author SabatiniJa
 *
 */
final class ImmutableExe {

	private final int index;
	private final List<String> elements;
	private final Map<String, String> couples;

	private ImmutableExe(ImmutableExeBuilder builder) {
		super();
		this.index = builder.index;
		this.elements = builder.elements;
		this.couples = builder.couples;
	}

	public int getIndex() {
		return index;
	}

	public List<String> getElements() {
		return elements;
	}

	public Map<String, String> getCouples() {
		return couples;
	}

	/**
	 * ImmutableExeBuilder pattern per creare un oggetto della classe ImmutabileExe
	 * 
	 * @author SabatiniJa
	 *
	 */
	public final static class ImmutableExeBuilder {
		private int index;
		private List<String> elements;
		private Map<String, String> couples;

		public int getIndex() {
			return index;
		}

		public ImmutableExeBuilder setIndex(int index) {
			this.index = index;
			return this;
		}

		public ImmutableExeBuilder setElements(List<String> elements) {
			this.elements = Collections.unmodifiableList(elements);
			return this;
		}

		public ImmutableExeBuilder setCouples(Map<String, String> couples) {
			this.couples = Collections.unmodifiableMap(couples);
			return this;
		}

		public ImmutableExe build() {
			return new ImmutableExe(this);
		}
	}

	@Override
	public String toString() {
		return "ImmutableExe [index=" + index + ", elements=" + elements + ", couples=" + couples + "]";
	}
}
