/*
 * This file is part of LibrePlan
 *
 * Copyright (C) 2015 LibrePlan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.libreplan.business.common.daos;

import org.libreplan.business.common.entities.Limits;

import java.util.List;

/**
 * DAO interface for the <code>Limits</code> entity.
 * Contract for {@link LimitsDAO}
 *
 * Created by
 * @author Vova Perebykivskiy <vova@libreplan-enterprise.com>
 * on 17.12.2015.
 */
public interface ILimitsDAO extends IGenericDAO<Limits, Long> {
    List<Limits> getAll();

    Limits getUsersType();
    Limits getResourcesType();
}
