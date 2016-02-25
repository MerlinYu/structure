package com.structure.person.fragment;

import android.nfc.Tag;

import com.structure.StructureApiService;
import com.structure.base.ActivityModule;
import com.structure.base.BaseModule;
import com.structure.base.fragment.BaseFragment;
import com.structure.base.fragment.BaseFragmentPresenter;
import com.structure.person.PersonApi;

/**
 * Created by yuchao.
 */
public class PersonFragmentPresenter extends BaseFragmentPresenter<PersonFragment, ActivityModule<PersonApi>> {

    private static final String TAG = "PersonFragmentPresenter";

    public PersonFragmentPresenter(PersonFragment baseFragment) {
        super(baseFragment, StructureApiService.create(PersonApi.class, TAG));
    }



}
