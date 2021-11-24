package org.opencds.cqf.cql.engine.fhir.retrieve;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseConformance;
import org.hl7.fhir.instance.model.api.ICompositeType;
import org.opencds.cqf.cql.engine.fhir.searchparam.SearchParameterMap;
import org.opencds.cqf.cql.engine.fhir.searchparam.SearchParameterResolver;
import org.opencds.cqf.cql.engine.runtime.Code;
import org.opencds.cqf.cql.engine.runtime.Interval;
import org.opencds.cqf.cql.engine.terminology.TerminologyProvider;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Dstu3FhirQueryGenerator extends BaseFhirQueryGenerator {
    public Dstu3FhirQueryGenerator(SearchParameterResolver searchParameterResolver, TerminologyProvider terminologyProvider) {
        super(searchParameterResolver, terminologyProvider, FhirContext.forDstu3());
    }

    @Override
    public List<String> generateFhirQueries(ICompositeType dreq, IBaseConformance capStatement) {
        if (!(dreq instanceof DataRequirement)) {
            throw new IllegalArgumentException("dataRequirement argument must be a DataRequirement");
        }
        if (capStatement != null && !(capStatement instanceof CapabilityStatement)) {
            throw new IllegalArgumentException("capabilityStatement argument must be a CapabilityStatement");
        }

        DataRequirement dataRequirement = (DataRequirement)dreq;
        CapabilityStatement capabilityStatement = (CapabilityStatement)capStatement;

        List<String> queries = new ArrayList<>();

        String codePath = null;
        List<Code> codes = null;
        String valueSet = null;

        if (dataRequirement.hasCodeFilter()) {
            for (org.hl7.fhir.dstu3.model.DataRequirement.DataRequirementCodeFilterComponent codeFilterComponent : dataRequirement.getCodeFilter()) {
                if (!codeFilterComponent.hasPath()) {
                    continue;
                }

                codePath = codeFilterComponent.getPath();

                // TODO: What to do if/when System is not provided...
                if (codeFilterComponent.hasValueCode()) {
                    List<org.hl7.fhir.dstu3.model.CodeType> codeFilterValueCode = codeFilterComponent.getValueCode();
                    for (CodeType codeType : codeFilterValueCode) {
                        Code code = new Code();
                        code.setCode(codeType.asStringValue());
                        codes.add(code);
                    }
                }
                if (codeFilterComponent.hasValueCoding()) {
                    codes = new ArrayList<Code>();

                    List<org.hl7.fhir.dstu3.model.Coding> codeFilterValueCodings = codeFilterComponent.getValueCoding();
                    for (Coding coding : codeFilterValueCodings) {
                        if (coding.hasCode()) {
                            Code code = new Code();
                            code.setSystem(coding.getSystem());
                            code.setCode(coding.getCode());
                            codes.add(code);
                        }
                    }
                }
                if (codeFilterComponent.hasValueCodeableConcept()) {
                    List<org.hl7.fhir.dstu3.model.CodeableConcept> codeFilterValueCodeableConcepts = codeFilterComponent.getValueCodeableConcept();
                    for (CodeableConcept codeableConcept : codeFilterValueCodeableConcepts) {
                        List<org.hl7.fhir.dstu3.model.Coding> codeFilterValueCodeableConceptCodings = codeableConcept.getCoding();
                        for (Coding coding : codeFilterValueCodeableConceptCodings) {
                            if (coding.hasCode()) {
                                Code code = new Code();
                                code.setSystem(coding.getSystem());
                                code.setCode(coding.getCode());
                                codes.add(code);
                            }
                        }
                    }
                }
                if (codeFilterComponent.hasValueSet()) {
                    if (codeFilterComponent.getValueSetReference().getReference() instanceof String) {
                        valueSet = ((Reference)codeFilterComponent.getValueSet()).getReference();
                    } else if (codeFilterComponent.getValueSetReference() instanceof Reference) {
                        valueSet = codeFilterComponent.getValueSetReference().getReference();
                    }
                }
            }
        }

        String datePath = null;
        String dateLowPath = null;
        String dateHighPath = null;
        Interval dateRange = null;
//        if (dataRequirement.hasDateFilter()) {
//            for (org.hl7.fhir.dstu3.model.DataRequirement.DataRequirementDateFilterComponent dateFilterComponent : dataRequirement.getDateFilter()) {
//                if (dateFilterComponent.hasPath() && dateFilterComponent.hasSearchParam()) {
//                    throw new UnsupportedOperationException(String.format("Either a path or a searchParam must be provided, but not both"));
//                }
//
//                if (dateFilterComponent.hasPath()) {
//                    datePath = dateFilterComponent.getPath();
//                } else if (dateFilterComponent.hasSearchParam()) {
//                    datePath = dateFilterComponent.getSearchParam();
//                }
//
//                Type dateFilterValue = dateFilterComponent.getValue();
//                if (dateFilterValue instanceof DateTimeType) {
//
//                } else if (dateFilterValue instanceof Duration) {
//
//                } else if (dateFilterValue instanceof Period) {
//
//                }
//            }
//        }

        List<SearchParameterMap> maps = new ArrayList<SearchParameterMap>();
        maps = setupQueries(null, null, null, dataRequirement.getType(), null,
            codePath, codes, valueSet, datePath, null, null, null);

        for (SearchParameterMap map : maps) {
            String query = null;
            try {
                query = URLDecoder.decode(map.toNormalizedQueryString(fhirContext), "UTF-8");
            } catch (Exception ex) {
                query = map.toNormalizedQueryString(fhirContext);
            }
            queries.add(dataRequirement.getType() + query);
        }

        return queries;
    }
}