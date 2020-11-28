package org.tut.micro.villain;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.tut.micro.villain.data.Villain;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class VillainService {

	@ConfigProperty(name = "level.multiplier", defaultValue="1")
    int levelMultiplier;

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Villain> findAllVillains() {
        return Villain.listAll();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Villain findVillainById(Long id) {
        return Villain.findById(id);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Villain findRandomVillain() {
        Villain randomVillain = null;
        while (randomVillain == null) {
            randomVillain = Villain.findRandom();
        }
        return randomVillain;
    }

    public Villain persistVillain(@Valid Villain villain) {
        villain.level = villain.level * levelMultiplier;
        villain.persist();
        return villain;
    }

    public Villain updateVillain(@Valid Villain villain) {
        Villain entity = Villain.findById(villain.id);
        entity.name = villain.name;
        entity.otherName = villain.otherName;
        entity.level = villain.level;
        entity.picture = villain.picture;
        entity.powers = villain.powers;
        return entity;
    }

    public void deleteVillain(Long id) {
        Villain villain = Villain.findById(id);
        villain.delete();
    }
    
}
