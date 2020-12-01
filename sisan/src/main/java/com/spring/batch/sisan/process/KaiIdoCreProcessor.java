package com.spring.batch.sisan.process;

import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.spring.batch.sisan.common.ErrInfoSave;
import com.spring.batch.sisan.common.HoyuZnDataSosa;
import com.spring.batch.sisan.common.ShiteNoSaiban;
import com.spring.batch.sisan.common.SinIdoKeyGet;
import com.spring.batch.sisan.common.SinZnKeyGet;
import com.spring.batch.sisan.dto.KaiIdoDto;
import com.spring.batch.sisan.dto.NryYmdIdoKomkDto;
import com.spring.batch.sisan.entities.BsIdEntity;
import com.spring.batch.sisan.entities.ThNrEntity;

@Component
public class KaiIdoCreProcessor implements ItemProcessor<KaiIdoDto, BsIdEntity> {

	private ExecutionContext jobExecutionContext;

	@Autowired
	private SinIdoKeyGet sinIdoKeyGet;

	@Autowired
	private SinZnKeyGet sinZnKeyGet;

	@Autowired
	private ShiteNoSaiban shiteNoSaiban;

	@Autowired
	private HoyuZnDataSosa hoyuZnDataSosa;

	@Autowired
	private ErrInfoSave errInfoSave;

	@Override
	public BsIdEntity process(KaiIdoDto kaiIdoDto) throws Exception {
		System.out.println("PROCESS START--------------");
		// 1. khoi tao BsIdEntity
		BsIdEntity bsIdEntityRlt = new BsIdEntity();
		// get thoong tin tu jobExecutionContext
		// Integer shiShoriKijunbi = (Integer) jobExecutionContext.get("shiShoriKijunbi");
		Integer shiShoriKijunbi = 20200707;

		// 2.1.truong hop kaiIdoDto.getThNrEntity() null
		BsIdEntity bsIdEntityInput = kaiIdoDto.getBsIdEntity();
		ThNrEntity thNrEntityInput = kaiIdoDto.getThNrEntity();
		if (thNrEntityInput == null) {
			// 2.1.1
			if (bsIdEntityInput != null && bsIdEntityInput.getYkjYmdKjy() <= shiShoriKijunbi
					&& bsIdEntityInput.getUkeYmdLcl() > shiShoriKijunbi) {
				System.out.println("createYkiKjiYmdIdo");
				bsIdEntityRlt = this.createYkiKjiYmdIdo(bsIdEntityInput, shiShoriKijunbi);
			} else if (bsIdEntityInput.getUkeYmdLcl() < shiShoriKijunbi) { // 2.1.2
				System.out.println("createLclUkeYmdIdo");
				bsIdEntityRlt = this.createLclUkeYmdIdo(bsIdEntityInput, shiShoriKijunbi);
			}
		} else {
			// 2.2.1
			if (thNrEntityInput.getDelKbn() == "") {
				System.out.println("createNrykYmdIdo");
				bsIdEntityRlt = this.createNrykYmdIdo(thNrEntityInput, shiShoriKijunbi);
			} else {
				System.out.println("createDelYmdIdo");
				bsIdEntityRlt = this.createDelYmdIdo(bsIdEntityInput, thNrEntityInput, shiShoriKijunbi);
			}
		}
		System.out.println("PROCESS END--------------");
		return bsIdEntityRlt;
	}

	private BsIdEntity createYkiKjiYmdIdo(BsIdEntity bsIdEntity, Integer shiShoriKijunbi) {
		// 1, 2
		BsIdEntity bsIdEntityRlt = copyBsIdEntity(bsIdEntity, shiShoriKijunbi);
		bsIdEntityRlt.setDelKbn(bsIdEntity.getDelKbn());
		bsIdEntityRlt.setUkeYmdKjy(bsIdEntity.getUkeYmdKjy());
		bsIdEntityRlt.setNrykUserId(bsIdEntity.getNrykUserId());
		return bsIdEntityRlt;
	}

	private BsIdEntity createLclUkeYmdIdo(BsIdEntity bsIdEntity, Integer shiShoriKijunbi) {
		// 1,2
		BsIdEntity bsIdEntityRlt = copyBsIdEntity(bsIdEntity, shiShoriKijunbi);
		bsIdEntityRlt.setDelKbn(bsIdEntity.getDelKbn());
		bsIdEntityRlt.setUkeYmdKjy(shiShoriKijunbi);
		bsIdEntityRlt.setNrykUserId(bsIdEntity.getNrykUserId());

		return bsIdEntityRlt;
	}

	private BsIdEntity createNrykYmdIdo(ThNrEntity thNrEntityInput, Integer shiShoriKijunbi) {
		BsIdEntity bsIdEntityRlt = new BsIdEntity();
		// 2.1
		Integer idoKey = sinIdoKeyGet.getSinIdoKey();
		// 2.2
		bsIdEntityRlt.setIdoKey(idoKey);
		bsIdEntityRlt.setDelKbn(thNrEntityInput.getDelKbn());
		bsIdEntityRlt.setKjnYmd(shiShoriKijunbi);
		bsIdEntityRlt.setUpdZnKeyTouitsu(0);
		StringBuilder innoinf = new StringBuilder("");
		innoinf.append(thNrEntityInput.getInnoinfRiyoCmpCd()).append(thNrEntityInput.getInnoinfCreKeitai())
				.append(thNrEntityInput.getInnoinfCreKjnYmd()).append(thNrEntityInput.getInnoinfNrykNo())
				.append(thNrEntityInput.getInnoinfEda());
		bsIdEntityRlt.setInnoinf(innoinf.toString());
		bsIdEntityRlt.setBunkatuMotoInnoinf("");
		bsIdEntityRlt.setItkCmpCd(thNrEntityInput.getItkCmpCd());
		bsIdEntityRlt.setFndCd(thNrEntityInput.getFndCd());
		bsIdEntityRlt.setMegCd(thNrEntityInput.getMegCd());
		bsIdEntityRlt.setBuySelKbn(thNrEntityInput.getBuySelKbn());
		bsIdEntityRlt.setFifoFlg("");
		bsIdEntityRlt.setKekaRskKbn(thNrEntityInput.getKekaRskKbn());
		bsIdEntityRlt.setKazeiKbn(thNrEntityInput.getKazeiKbn());
		bsIdEntityRlt.setAgainstKbn(thNrEntityInput.getAgainstKbn());
		bsIdEntityRlt.setHokanBnkCd(thNrEntityInput.getHokanBnkCd());
		bsIdEntityRlt.setErrorFlg("");
		bsIdEntityRlt.setYkjYmdLcl(thNrEntityInput.getYkjYmdLcl());
		bsIdEntityRlt.setUkeYmdLcl(thNrEntityInput.getUkeYmdLcl());
		bsIdEntityRlt.setUkeYmdJpn(thNrEntityInput.getUkeYmdJpn());
		bsIdEntityRlt.setNrykYmd(shiShoriKijunbi);
		bsIdEntityRlt.setYkjYmdKjy(thNrEntityInput.getYkjYmdKjy());
		if (thNrEntityInput.getUkeYmdLcl() <= shiShoriKijunbi) {
			bsIdEntityRlt.setUkeYmdKjy(shiShoriKijunbi);
		} else {
			bsIdEntityRlt.setUkeYmdKjy(0);
		}
		bsIdEntityRlt.setSikinUkeKanrYmdK(thNrEntityInput.getSikinUkeKanrYmd());
		// TODO
		bsIdEntityRlt.setSecUkeKanrYmdKjy(0);
		bsIdEntityRlt.setFace(thNrEntityInput.getFace());
		bsIdEntityRlt.setOrgFace(thNrEntityInput.getOrgFace());
		bsIdEntityRlt.setYkjTnk(thNrEntityInput.getYkjTnk());
		bsIdEntityRlt.setYkjKn(thNrEntityInput.getYkjKn());
		bsIdEntityRlt.setKekaRsk(thNrEntityInput.getKekaRsk());
		bsIdEntityRlt.setNrykUserId(thNrEntityInput.getNrykUserId());

		// 2.3
		// 2.3.1
		NryYmdIdoKomkDto nryYmdIdoKomkDto = null;
		String fndCd = thNrEntityInput.getFndCd();
		String megCd = thNrEntityInput.getMegCd();
		Integer kaiYmd = thNrEntityInput.getKaiYmd();
		Integer siteiNo = thNrEntityInput.getSiteiNo();
		if (!"1".equals(thNrEntityInput.getHibakariTrhkKbn())) {
			if (thNrEntityInput.getKaiYmd() == 0 && thNrEntityInput.getSiteiNo() == 0) {
				Integer ykjYmdLcl = thNrEntityInput.getYkjYmdLcl();
				nryYmdIdoKomkDto = this.editKaiYmdMiSiteiNoSitei(fndCd, megCd, ykjYmdLcl);
			} else if (thNrEntityInput.getKaiYmd() != 0 && thNrEntityInput.getSiteiNo() != 0) {
				nryYmdIdoKomkDto = this.editKaiYmdSiteiNoSitei(fndCd, megCd, kaiYmd, siteiNo);
			}
		} else {
			nryYmdIdoKomkDto = this.editHibakari(kaiYmd, siteiNo);
		}

		// 2.4
		if (nryYmdIdoKomkDto != null) {
			bsIdEntityRlt.setUpdZnKey(nryYmdIdoKomkDto.getUpdZnKey());
			bsIdEntityRlt.setKaiYmd(nryYmdIdoKomkDto.getKaiYmd());
			bsIdEntityRlt.setSiteiNo(nryYmdIdoKomkDto.getSiteiNo());
			if ("1".equals(nryYmdIdoKomkDto.getErrorFlg())) {
				bsIdEntityRlt.setErrorFlg("1");
			}

		}

		// 3
		return bsIdEntityRlt;
	}

	private BsIdEntity createDelYmdIdo(BsIdEntity bsIdEntity, ThNrEntity thNrEntityInput, Integer shiShoriKijunbi) {
		// 1,2
		BsIdEntity bsIdEntityRlt = copyBsIdEntity(bsIdEntity, shiShoriKijunbi);
		bsIdEntityRlt.setDelKbn(thNrEntityInput.getDelKbn());
		bsIdEntityRlt.setUkeYmdKjy(bsIdEntity.getUkeYmdKjy());
		bsIdEntityRlt.setNrykUserId(thNrEntityInput.getNrykUserId());

		// 3
		return bsIdEntityRlt;
	}

	private NryYmdIdoKomkDto editKaiYmdMiSiteiNoSitei(String fndCd, String megCd, Integer ykjYmdLcl) {
		// 1
		NryYmdIdoKomkDto nryYmdIdoKomkDto = new NryYmdIdoKomkDto();

		// 2.1
		Integer updZnKey = sinZnKeyGet.getSinZnKey();

		// 2.2
		Integer siteiNo = shiteNoSaiban.getSiteNo(fndCd, megCd, ykjYmdLcl);

		// 2.3
		nryYmdIdoKomkDto.setUpdZnKey(updZnKey);
		nryYmdIdoKomkDto.setKaiYmd(ykjYmdLcl);
		nryYmdIdoKomkDto.setSiteiNo(siteiNo);

		return nryYmdIdoKomkDto;
	}

	private NryYmdIdoKomkDto editKaiYmdSiteiNoSitei(String fndCd, String megCd, Integer kaiYmd, Integer siteiNo) {
		// 1
		NryYmdIdoKomkDto nryYmdIdoKomkDto = new NryYmdIdoKomkDto();

		// 2.1
		Integer znKey = hoyuZnDataSosa.getZnKey(fndCd, megCd, kaiYmd, siteiNo);
		if (znKey != null) { // 2.1.1
			List<String> existsZnKeyList = hoyuZnDataSosa.checkKaiZnJufuku(znKey);
			if (!CollectionUtils.isEmpty(existsZnKeyList)) {
				errInfoSave.saveErrInfo(znKey, "107");
			}

			nryYmdIdoKomkDto.setErrorFlg("1");
		} else {
			znKey = sinZnKeyGet.getSinZnKey();
		}

		// 2.2
		nryYmdIdoKomkDto.setUpdZnKey(znKey);
		nryYmdIdoKomkDto.setKaiYmd(kaiYmd);
		nryYmdIdoKomkDto.setSiteiNo(siteiNo);

		return nryYmdIdoKomkDto;
	}

	private NryYmdIdoKomkDto editHibakari(Integer kaiYmd, Integer siteiNo) {
		// 1
		NryYmdIdoKomkDto nryYmdIdoKomkDto = new NryYmdIdoKomkDto();

		// 2.1
		Integer znKey = sinZnKeyGet.getSinZnKey();

		// 2.2
		nryYmdIdoKomkDto.setUpdZnKey(znKey);
		nryYmdIdoKomkDto.setKaiYmd(kaiYmd);
		nryYmdIdoKomkDto.setSiteiNo(siteiNo);

		return nryYmdIdoKomkDto;
	}

	private BsIdEntity copyBsIdEntity(BsIdEntity bsIdEntity, Integer shiShoriKijunbi) {
		BsIdEntity bsIdEntityRlt = new BsIdEntity();

		bsIdEntityRlt.setIdoKey(bsIdEntity.getIdoKey());
		bsIdEntityRlt.setKjnYmd(shiShoriKijunbi);
		bsIdEntityRlt.setUpdZnKey(bsIdEntity.getUpdZnKey());
		bsIdEntityRlt.setUpdZnKeyTouitsu(bsIdEntity.getUpdZnKeyTouitsu());
		bsIdEntityRlt.setInnoinf(bsIdEntity.getInnoinf());
		bsIdEntityRlt.setBunkatuMotoInnoinf(bsIdEntity.getBunkatuMotoInnoinf());
		bsIdEntityRlt.setItkCmpCd(bsIdEntity.getItkCmpCd());
		bsIdEntityRlt.setFndCd(bsIdEntity.getFndCd());
		bsIdEntityRlt.setMegCd(bsIdEntity.getMegCd());
		bsIdEntityRlt.setKaiYmd(bsIdEntity.getKaiYmd());
		bsIdEntityRlt.setSiteiNo(bsIdEntity.getSiteiNo());
		bsIdEntityRlt.setBuySelKbn(bsIdEntity.getBuySelKbn());
		bsIdEntityRlt.setFifoFlg(bsIdEntity.getFifoFlg());
		bsIdEntityRlt.setKekaRskKbn(bsIdEntity.getKekaRskKbn());
		bsIdEntityRlt.setKazeiKbn(bsIdEntity.getKazeiKbn());
		bsIdEntityRlt.setAgainstKbn(bsIdEntity.getAgainstKbn());
		bsIdEntityRlt.setHokanBnkCd(bsIdEntity.getHokanBnkCd());
		bsIdEntityRlt.setErrorFlg("");
		bsIdEntityRlt.setYkjYmdLcl(bsIdEntity.getYkjYmdLcl());
		bsIdEntityRlt.setUkeYmdLcl(bsIdEntity.getUkeYmdLcl());
		bsIdEntityRlt.setUkeYmdJpn(bsIdEntity.getUkeYmdJpn());
		bsIdEntityRlt.setNrykYmd(bsIdEntity.getNrykYmd());
		bsIdEntityRlt.setYkjYmdKjy(bsIdEntity.getYkjYmdKjy());
		bsIdEntityRlt.setSikinUkeKanrYmdK(bsIdEntity.getSikinUkeKanrYmdK());
		bsIdEntityRlt.setSecUkeKanrYmdKjy(bsIdEntity.getSecUkeKanrYmdKjy());
		bsIdEntityRlt.setFace(bsIdEntity.getFace());
		bsIdEntityRlt.setOrgFace(bsIdEntity.getOrgFace());
		bsIdEntityRlt.setYkjTnk(bsIdEntity.getYkjTnk());
		bsIdEntityRlt.setYkjKn(bsIdEntity.getYkjKn());
		bsIdEntityRlt.setKekaRsk(bsIdEntity.getKekaRsk());
		bsIdEntityRlt.setUkeKn(bsIdEntity.getUkeKn());

		return bsIdEntityRlt;
	}

	@BeforeStep
	private final void saveStepExecution(StepExecution stepExecution) {
		this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
	}

}
