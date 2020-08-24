package com.iiht.evaluation.coronokit.service;

import java.util.List;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.KitItemDetails;


public interface KitService {

	KitDetail validateAndAdd(KitDetail kitDetail, KitDao kitDAO) throws ProductException;
	KitDetail validateAndSave(KitDetail kitDetail, KitDao kitDAO) throws ProductException;
	boolean deleteKitItem(int kitId, KitDao kitDAO) throws ProductException;
	List<KitItemDetails> getAllKitItems(int kitId, KitDao kitDAO) throws ProductException;
	CoronaKit validateAndAddCoronaKit(CoronaKit coronoKit, KitDao kitDAO) throws ProductException;
	CoronaKit validateAndSaveCoronaKit(int coronaKitId, CoronaKit coronoKit, KitDao kitDAO) throws ProductException;
	CoronaKit getCoronaKit(int coronoKitId, KitDao kitDAO) throws ProductException;
}
