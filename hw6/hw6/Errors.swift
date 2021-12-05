//
//  Errors.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public enum ArithExpressionError: Error {
    case parsingError
    case evaluatingError
    case customError(_ description: String?)
}
