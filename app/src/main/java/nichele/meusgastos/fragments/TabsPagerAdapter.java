package nichele.meusgastos.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import nichele.meusgastos.util.TipoDado;

public class TabsPagerAdapter extends FragmentPagerAdapter {

   private  String[] tabs;
   public TabsPagerAdapter(FragmentManager fm, String[] ptabs) {
      super(fm);
      tabs=ptabs;
   }

   @Override
   public Fragment getItem(int position) {
      switch (position){
         case  0:
            return new fraListCategorias(TipoDado.entradas);
         case 1:
            return new fraListCategorias(TipoDado.saidas);
      }
      return null;
   }

   @Override
   public int getCount() {
      return tabs.length;
   }

   @Override
   public CharSequence getPageTitle(int position) {
      return tabs[position];
   }
}
