package nichele.meusgastos.exemplo_recview_group;

public class ChildModel implements iRelatorio {
	String child;

	public void setChild(String child) {
		this.child = child;
	}

	@Override
	public boolean isHeader() {
		return false;
	}

	@Override
	public String getName() {
		return child;
	}
}
