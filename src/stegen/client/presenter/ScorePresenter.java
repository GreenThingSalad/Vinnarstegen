package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.score.ScoreCellTable2.ScoreCell;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class ScorePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	final EventCallback<?> eventPlayerWonCallback = createPlayerWonCallback();
	final EventCallback<?> eventChangedScoresCallback = creatEventChangedScoresCallback();
	final ClickHandler clickCleanScoresHandler = createClickCleanScoresHandler();

	public interface Display {
		void addCleanScoresHandler(ClickHandler clickHandler);

		void changeScoreList(List<ScoreCell> content);
	}

	public ScorePresenter(Display scoreView, LoginDataDto result, EventBus eventBus) {
		this.view = scoreView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
		initEvents();
		loadScores();
	}

	private void initView() {
		view.addCleanScoresHandler(clickCleanScoresHandler);

	}

	private void initEvents() {
		eventBus.addHandler(eventPlayerWonCallback);
		eventBus.addHandler(eventChangedScoresCallback);
	}

	private void loadScores() {
		eventBus.updatePlayerScoreList();
	}

	private ClickHandler createClickCleanScoresHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}
		};
	}

	private PlayerWonCallback createPlayerWonCallback() {
		return new PlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				eventBus.updatePlayerScoreList();
			}
		};
	}

	private UpdatePlayerScoreListCallback creatEventChangedScoresCallback() {
		return new UpdatePlayerScoreListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerScoreDto> scores) {
				List<ScoreCell> content = new ArrayList<ScoreCell>();
				for (PlayerScoreDto playerScoreDto : scores) {
					boolean currentUser = playerScoreDto.player.email.equals(result.player.email);
					content.add(new ScoreCellImpl(playerScoreDto.player.nickname, "" + playerScoreDto.score, ""
							+ playerScoreDto.ranking, playerScoreDto.changedDateTime,
							playerScoreDto.changedBy.nickname, currentUser));
				}
				view.changeScoreList(content);
			}
		};
	}

	private static class ScoreCellImpl implements ScoreCell {
		private final String name;
		private final String score;
		private final String ranking;
		private final String changedDateTime;
		private final String changedBy;
		private final boolean currentUser;

		public ScoreCellImpl(String name, String score, String ranking, String changedDateTime, String changedBy,
				boolean currentUser) {
			this.name = name;
			this.score = score;
			this.ranking = ranking;
			this.changedDateTime = changedDateTime;
			this.changedBy = changedBy;
			this.currentUser = currentUser;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getScore() {
			return score;
		}

		@Override
		public String getRanking() {
			return ranking;
		}

		@Override
		public String getChangedDateTime() {
			return changedDateTime;
		}

		@Override
		public String getChangedBy() {
			return changedBy;
		}

		@Override
		public boolean isCurrentUser() {
			return currentUser;
		}

	}
}
