package nichele.meusgastos.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {

   private  String[] tabs;
   public FragmentPageAdapter(FragmentManager fm, String[] ptabs) {
      super(fm);
      tabs=ptabs;
   }

   @Override
   public Fragment getItem(int position) {
      switch (position){
         case  0:
            return new fraGerCatReceitas();
         case 1:
            return new fraGerCatDespesas();
      }
      return null;
   }

   @Override
   public int getCount() {
      return tabs.length;
   }
}
