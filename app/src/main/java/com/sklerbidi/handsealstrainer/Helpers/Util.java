package com.sklerbidi.handsealstrainer.Helpers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Util {
    public static void openFragment(FragmentManager fragmentManager, int containerId, Fragment fragment, String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment, fragmentTag);
        if (addToBackStack) {
            transaction.addToBackStack(fragmentTag); // Add the fragment to the back stack
        }
        transaction.commit();
    }

    public static void openFragmentAfterLoading(FragmentManager fragmentManager, int containerId, Fragment fragment, String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment, fragmentTag);

        if (addToBackStack) {
            int backStackCount = fragmentManager.getBackStackEntryCount();
            if (backStackCount > 0) {
                FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(backStackCount - 1);
                fragmentManager.popBackStack(backStackEntry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            transaction.addToBackStack(fragmentTag);
        }

        transaction.commit();
    }
}
