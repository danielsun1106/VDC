package org.reluxa.bid.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.reluxa.bid.Bid;
import org.reluxa.bid.service.BidEvaluator;
import org.reluxa.settings.Config;
import org.reluxa.settings.service.SettingsServiceIF;
import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.widget.AbstractColumnGenerator;
import org.reluxa.vaadin.widget.Icon;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class BidScoreGenerator extends AbstractColumnGenerator<Bid, Component> {

	@Inject
	BidEvaluator bidEvaluator;
	
	@Inject
	TimeServiceIF timeService;
	
	@Inject
	SettingsServiceIF settings;

	@Override 
  public Component generateCell(Bid current) {
    Collection<Bid> bids = bidEvaluator.evaluteBidsForCurrentWeek();
    Collection<Bid> winners = getWinners(bids);
    for (Bid bid : bids) {
      if (bid.getId() == current.getId() && bid.getScore() != null) {
      	return new Label(getWinnerThrophy(bid, winners)+Double.toString(bid.getScore()),ContentMode.HTML);
      }
    }
    return null;
  }
	
	
	private String getWinnerThrophy(Bid bid, Collection<Bid> winners) {
		
		for (Bid bid2 : winners) {
	    if (bid2.equals(bid)) {
	    	System.out.println("equals: "+bid+"\t\t"+bid2);
	    }
    }
		
		if (winners.contains(bid)) {
			return Icon.get("trophy","green").toString();
		}
		return StringUtils.EMPTY;
	}
	
	
	private Collection<Bid> getWinners(Collection<Bid> all) {
		Config config = settings.getConfig();
		all = Collections2.filter(all, new Predicate<Bid>(){
			@Override
      public boolean apply(@Nullable Bid bid) {
				return timeService.getWeekBegin().before(bid.getCreationTime());
      }
		});
		
		ArrayList<Bid> sorted = new ArrayList<>(all);
		Collections.sort(sorted, BidEvaluator.SCORE_COMPARATOR);
		if (sorted.size() <  config.getNumberOfEventsPerWeek()) {
			return sorted;
		}
		return new ArrayList<>(sorted.subList(0, config.getNumberOfEventsPerWeek()));
	}
}
