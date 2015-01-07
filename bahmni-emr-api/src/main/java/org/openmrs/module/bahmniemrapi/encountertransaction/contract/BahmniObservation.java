package org.openmrs.module.bahmniemrapi.encountertransaction.contract;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.Obs;
import org.openmrs.module.bahmniemrapi.obsrelation.contract.ObsRelationship;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;
import org.openmrs.module.emrapi.utils.CustomJsonDateSerializer;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BahmniObservation implements Comparable<BahmniObservation>{

    private Date encounterDateTime;
    private Date visitStartDateTime;
    private ObsRelationship targetObsRelation;
    private EncounterTransaction.Observation encounterTransactionObservation;
    private Collection<BahmniObservation> groupMembers = new ArrayList<>();
    public Set<EncounterTransaction.Provider> providers;
    private Boolean isAbnormal;
    private Long duration;
    private String type;
    private String encounterUuid;

    private int conceptSortWeight;

    public BahmniObservation() {
        encounterTransactionObservation = new EncounterTransaction.Observation();
    }

    public EncounterTransaction.Concept getConcept() {
        return encounterTransactionObservation.getConcept();
    }

    public BahmniObservation setConcept(EncounterTransaction.Concept concept) {
        encounterTransactionObservation.setConcept(concept);
        return this;
    }

    public Object getValue() {
        return encounterTransactionObservation.getValue();
    }

    public BahmniObservation setValue(Object value) {
        encounterTransactionObservation.setValue(value);
        return this;
    }

    public String getComment() {
        return encounterTransactionObservation.getComment();
    }

    public BahmniObservation setComment(String comment) {
        encounterTransactionObservation.setComment(comment);
        return this;
    }

    public BahmniObservation setVoided(boolean voided) {
        encounterTransactionObservation.setVoided(voided);
        return this;
    }

    public boolean getVoided() {
        return encounterTransactionObservation.getVoided();
    }

    public String getVoidReason() {
        return encounterTransactionObservation.getVoidReason();
    }

    public BahmniObservation setVoidReason(String voidReason) {
        encounterTransactionObservation.setVoidReason(voidReason);
        return this;
    }

    public Collection<BahmniObservation> getGroupMembers() {
        return new TreeSet<>(this.groupMembers);
    }

    public void setGroupMembers(Collection<BahmniObservation> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void addGroupMember(BahmniObservation observation) {
        groupMembers.add(observation);
    }

    public void removeGroupMembers(Collection<BahmniObservation> observations) {
        groupMembers.removeAll(observations);
    }

    public String getOrderUuid() {
        return encounterTransactionObservation.getOrderUuid();
    }

    public BahmniObservation setOrderUuid(String orderUuid) {
        encounterTransactionObservation.setOrderUuid(orderUuid);
        return this;
    }

    public BahmniObservation setObservationDateTime(Date observationDateTime) {
        encounterTransactionObservation.setObservationDateTime(observationDateTime);
        return this;
    }

    public String getUuid() {
        return encounterTransactionObservation.getUuid();
    }

    public BahmniObservation setUuid(String uuid) {
        encounterTransactionObservation.setUuid(uuid);
        return this;
    }

    @JsonSerialize(using = CustomJsonDateSerializer.class)
    public Date getObservationDateTime() {
        return encounterTransactionObservation.getObservationDateTime();
    }

    public boolean isSameAs(EncounterTransaction.Observation encounterTransactionObservation) {
        return this.getUuid().equals(encounterTransactionObservation.getUuid());
    }

    public ObsRelationship getTargetObsRelation() {
        return targetObsRelation;
    }

    public void setTargetObsRelation(ObsRelationship targetObsRelation) {
        this.targetObsRelation = targetObsRelation;
    }

    public EncounterTransaction.Observation toETObservation() {
        List<EncounterTransaction.Observation> observations = new ArrayList<>();
        for (BahmniObservation groupMember : this.groupMembers) {
            observations.add(groupMember.toETObservation());
        }
        encounterTransactionObservation.setGroupMembers(observations);
        return this.encounterTransactionObservation;
    }

    public String getConceptUuid() {
        return encounterTransactionObservation.getConceptUuid();
    }

    public boolean isSameAs(Obs obs) {
        return this.getUuid().equals(obs.getUuid());
    }

    public static List<EncounterTransaction.Observation> toETObsFromBahmniObs(Collection<BahmniObservation> bahmniObservations) {
        List<EncounterTransaction.Observation> etObservations = new ArrayList<>();
        for (BahmniObservation bahmniObservation : bahmniObservations) {
            etObservations.add(bahmniObservation.toETObservation());
        }
        return etObservations;
    }

    public boolean hasTargetObsRelation() {
        return targetObsRelation != null && targetObsRelation.getTargetObs() != null;
    }

    public Set<EncounterTransaction.Provider> getProviders() {
        return providers;
    }

    public void setProviders(Set<EncounterTransaction.Provider> providers) {
        this.providers = providers;
    }
    
    public Boolean getIsAbnormal() {
        return isAbnormal;
    }

    public Boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(Boolean abnormal) {
        isAbnormal = abnormal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getEncounterDateTime() {
        return encounterDateTime;
    }

    public void setEncounterDateTime(Date encounterDateTime) {
        this.encounterDateTime = encounterDateTime;
    }

    public Integer getConceptSortWeight() {
        return conceptSortWeight;
    }

    public void setConceptSortWeight(Integer conceptSortWeight) {
        this.conceptSortWeight = conceptSortWeight;
    }

    public void setEncounterTransactionObservation(EncounterTransaction.Observation encounterTransactionObservation) {
        this.encounterTransactionObservation = encounterTransactionObservation;
    }

    @JsonSerialize(using = CustomJsonDateSerializer.class)
    public Date getVisitStartDateTime() {
        return visitStartDateTime;
    }

    public void setVisitStartDateTime(Date visitStartDateTime) {
        this.visitStartDateTime = visitStartDateTime;
    }

    @Override
    public int compareTo(BahmniObservation o) {
        //ensure you dont give a zero, as TreeSets delete them if there is no sort weight
        return getConceptSortWeight() > o.getConceptSortWeight() ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BahmniObservation that = (BahmniObservation) o;

        if (conceptSortWeight != that.conceptSortWeight) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (encounterDateTime != null ? !encounterDateTime.equals(that.encounterDateTime) : that.encounterDateTime != null)
            return false;
        if (encounterTransactionObservation != null ? !encounterTransactionObservation.equals(that.encounterTransactionObservation) : that.encounterTransactionObservation != null)
            return false;
        if (groupMembers != null ? !groupMembers.equals(that.groupMembers) : that.groupMembers != null) return false;
        if (isAbnormal != null ? !isAbnormal.equals(that.isAbnormal) : that.isAbnormal != null) return false;
        if (providers != null ? !providers.equals(that.providers) : that.providers != null) return false;
        if (targetObsRelation != null ? !targetObsRelation.equals(that.targetObsRelation) : that.targetObsRelation != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (visitStartDateTime != null ? !visitStartDateTime.equals(that.visitStartDateTime) : that.visitStartDateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = encounterDateTime != null ? encounterDateTime.hashCode() : 0;
        result = 31 * result + (visitStartDateTime != null ? visitStartDateTime.hashCode() : 0);
        result = 31 * result + (targetObsRelation != null ? targetObsRelation.hashCode() : 0);
        result = 31 * result + (encounterTransactionObservation != null ? encounterTransactionObservation.hashCode() : 0);
        result = 31 * result + (groupMembers != null ? groupMembers.hashCode() : 0);
        result = 31 * result + (providers != null ? providers.hashCode() : 0);
        result = 31 * result + (isAbnormal != null ? isAbnormal.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + conceptSortWeight;
        return result;
    }

    public String getEncounterUuid() {
        return encounterUuid;
    }

    public void setEncounterUuid(String encounterUuid) {
        this.encounterUuid = encounterUuid;
    }
}
